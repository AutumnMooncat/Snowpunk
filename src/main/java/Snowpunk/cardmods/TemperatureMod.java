package Snowpunk.cardmods;

import Snowpunk.actions.ExhumeRandomCardToDrawPileAction;
import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.LoopcastField;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID(TemperatureMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    public TemperatureMod() {
        this.priority = -2;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        switch (CardTemperatureFields.getCardHeat(card)) {
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
        int heat = CardTemperatureFields.getCardHeat(card);
        int amount = card instanceof MultiTempEffectCard ? ((MultiTempEffectCard) card).tempEffectAmount() : 1;
        Wiz.atb(new ModEngineTempAction(heat*amount));
        if (heat > 0) {
            /*if (card.hasTag(CustomTags.VENT)) {
                Wiz.atb(new ModCardTempAction(card, -heat));
            } else {
                action.exhaustCard = true;
            }*/
            //Wiz.atb(new GainEnergyAction(amount));
            Wiz.applyToSelf(new SteamPower(Wiz.adp(), 1));
        }
        if (heat == 2 && !LoopcastField.LoopField.islooping.get(card)) {
            for (int i = 0; i < amount ; i++) {
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (target instanceof AbstractMonster) {
                    tmp.calculateCardDamage((AbstractMonster) target);
                }
                tmp.purgeOnUse = true;
                //Don't loop infinitely, lol
                LoopcastField.LoopField.islooping.set(tmp, true);
                if (target instanceof AbstractMonster) {
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, (AbstractMonster) target, card.energyOnUse, true, true), true);
                } else {
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, null, card.energyOnUse, true, true), true);
                }
            }
            /*card.use(Wiz.adp(), (AbstractMonster) target);
            for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
                if (!(mod instanceof TemperatureMod)) {
                    for (int i = 0 ; i < amount ; i++) {
                        mod.onUse(card, target, action);
                    }
                }
            }*/
        }
        if (heat == -2) {
            Wiz.atb(new ExhumeRandomCardToDrawPileAction(c -> !c.hasTag(AbstractCard.CardTags.HEALING)));
        }
        if (heat < 0) {
            Wiz.atb(new DrawCardAction(amount));
        }
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        if (!card.isEthereal && CardTemperatureFields.getCardHeat(card) < 0) {
            card.retain = true;
        }
        return false;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            card.initializeDescription();
            return false;
        }
        return true;
    }


    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TemperatureMod();
    }
}
