package Snowpunk.cardmods;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID("TemperatureMod");
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    public static final Color HOT_TINT = new Color(1, 209/255f, 209/255f, 1);
    public static final Color COLD_TINT = new Color(209/255f, 253/255f, 1, 1);
    public static final Color OVERHEAT_TINT = new Color(1, 130/255f, 130/255f, 1);
    public static final Color FROZEN_TINT = new Color(130/255f, 251/255f, 1, 1);

    int heat;
    int inherentHeat;

    public TemperatureMod(boolean inherent, int heat) {
        if (inherent) {
            inherentHeat = heat;
        }
        this.heat = heat;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        switch (heat) {
            case -2:
                return TEXT[3] + rawDescription;
            case -1:
                return TEXT[2] + rawDescription;
            case 1:
                return TEXT[0] + rawDescription;
            case 2 :
                return TEXT[1] + rawDescription;
        }
        return rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new ModEngineTempAction(heat));
        if (heat > 0) {
            action.exhaustCard = true;
            Wiz.atb(new GainEnergyAction(1));
        }
        //TODO make this not play a copy at the same temp
        if (heat == 2) {
            card.use(Wiz.adp(), (AbstractMonster) target);
        }
        /*if (heat == 2) {
            if (!card.purgeOnUse) {// 46
                AbstractMonster m = null;// 50
                if (action.target != null) {// 52
                    m = (AbstractMonster)action.target;// 53
                }

                AbstractCard tmp = card.makeSameInstanceOf();// 56
                AbstractDungeon.player.limbo.addToBottom(tmp);// 57
                tmp.current_x = card.current_x;// 58
                tmp.current_y = card.current_y;// 59
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;// 60
                tmp.target_y = (float)Settings.HEIGHT / 2.0F;// 61
                if (m != null) {// 63
                    tmp.calculateCardDamage(m);// 64
                }

                tmp.purgeOnUse = true;// 67
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);// 68
            }
        }*/
        if (heat < 0) {
            Wiz.atb(new DrawCardAction(1));
        }
        if (heat == -2) {
            Wiz.applyToSelf(new SnowballPower(Wiz.adp(), 1));
        }
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        if (heat < 0) {
            card.retain = true;
        }
        return false;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            TemperatureMod tm = (TemperatureMod) CardModifierManager.getModifiers(card, ID).get(0);
            tm.heat += heat;
            tm.inherentHeat += inherentHeat;
            if (tm.heat < -2) {
                tm.heat = -2;
            } else if (tm.heat > 2) {
                tm.heat = 2;
            }
            if (tm.inherentHeat < -2) {
                tm.inherentHeat = -2;
            } else if (tm.inherentHeat > 2) {
                tm.inherentHeat = 2;
            }
            tm.flashHeatColor(card);
            card.initializeDescription();
            return false;
        }
        if (heat < -2) {
            heat = -2;
        } else if (heat > 2) {
            heat = 2;
        }
        if (inherentHeat < -2) {
            inherentHeat = -2;
        } else if (inherentHeat > 2) {
            inherentHeat = 2;
        }
        flashHeatColor(card);
        return true;
    }

    public void flashHeatColor(AbstractCard c) {
        switch (heat) {
            case -2:
                c.superFlash(FROZEN_TINT.cpy());
                break;
            case -1 :
                c.superFlash(COLD_TINT.cpy());
                break;
            case 0:
                c.superFlash(Color.WHITE.cpy());
                break;
            case 1:
                c.superFlash(HOT_TINT.cpy());
                break;
            case 2:
                c.superFlash(OVERHEAT_TINT.cpy());
                break;
        }
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return inherentHeat - heat == 0;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TemperatureMod(false, heat);
    }

    public static int getCardHeat(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            TemperatureMod tm = (TemperatureMod) CardModifierManager.getModifiers(card, ID).get(0);
            return tm.heat;
        }
        return 0;
    }

    public static Color getCardTint(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            TemperatureMod tm = (TemperatureMod) CardModifierManager.getModifiers(card, ID).get(0);
            switch (tm.heat) {
                case -2:
                    return FROZEN_TINT;
                case -1:
                    return COLD_TINT;
                case 1:
                    return HOT_TINT;
                case 2:
                    return OVERHEAT_TINT;
            }
        }
        return null;
    }
}
