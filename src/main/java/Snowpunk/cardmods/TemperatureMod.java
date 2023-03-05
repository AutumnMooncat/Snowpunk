package Snowpunk.cardmods;

import Snowpunk.actions.*;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.patches.LoopcastField;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
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

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID(TemperatureMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/icons/Temp.png");
    private static final Texture hot = TexLoader.getTexture(modID + "Resources/images/icons/Hot.png");
    private static final Texture cold = TexLoader.getTexture(modID + "Resources/images/icons/Cold.png");

    public static final int FROZEN = -2, COLD = -1, HOT = 1, OVERHEATED = 2;

    public TemperatureMod() {
        this.priority = -2;
    }

    private static ArrayList<TooltipInfo> CondenseTip, EvapTip, Tooltip;

    @Override
    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new PrefixManager());
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (EvapTip == null) {
            EvapTip = new ArrayList<>();
            EvapTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.EVAPORATE), BaseMod.getKeywordDescription(KeywordManager.EVAPORATE)));
        }
        if (CondenseTip == null) {
            CondenseTip = new ArrayList<>();
            CondenseTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.CONDENSE), BaseMod.getKeywordDescription(KeywordManager.CONDENSE)));
        }
        if (Tooltip == null)
            Tooltip = new ArrayList<>();

        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat <= COLD && !card.keywords.contains(KeywordManager.CONDENSE))
            return CondenseTip;

        if (heat >= HOT && !card.keywords.contains(KeywordManager.EVAPORATE))
            return EvapTip;

        return Tooltip;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int heat = CardTemperatureFields.getCardHeat(card);
        int amount = card instanceof MultiTempEffectCard ? ((MultiTempEffectCard) card).tempEffectAmount() : 1;
        if (heat >= HOT) {
            Wiz.atb(new GainEnergyAction(amount));
            EvaporatePanelPatches.EvaporateField.evaporate.set(card, true);
        }
        if ((heat == OVERHEATED) && !LoopcastField.LoopField.islooping.get(card)) {
            for (int i = 0; i < amount; i++) {
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
        }

        if (heat <= COLD)
            Wiz.atb(new CondenseAction());

        if (heat == FROZEN)
            Wiz.atb(new DrawCardAction(amount));

    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        if (!card.isEthereal && CardTemperatureFields.getCardHeat(card) < 0)
            card.retain = true;
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
    public void onRender(AbstractCard card, SpriteBatch sb) {
        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat >= HOT)
            ExtraIcons.icon(hot).text(String.valueOf(heat)).render(card);
        if (heat <= COLD)
            ExtraIcons.icon(cold).text(String.valueOf(heat)).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat >= HOT)
            ExtraIcons.icon(hot).text(String.valueOf(heat)).render(card);
        if (heat <= COLD)
            ExtraIcons.icon(cold).text(String.valueOf(heat)).render(card);
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
