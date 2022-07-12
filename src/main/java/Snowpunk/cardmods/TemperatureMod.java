package Snowpunk.cardmods;

import Snowpunk.actions.CondenseRandomCardToDrawPileAction;
import Snowpunk.actions.ExhumeRandomCardToDrawPileAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.actions.PullCardAction;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.patches.LoopcastField;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
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

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID(TemperatureMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    public TemperatureMod() {
        this.priority = -2;
    }

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        switch (CardTemperatureFields.getCardHeat(card)) {
            case -2:
                if (card.hasTag(CustomTags.FLUX)) {
                    return TEXT[5] + rawDescription;
                }
                return TEXT[3] + rawDescription;
            case -1:
                if (card.hasTag(CustomTags.FLUX)) {
                    return TEXT[4] + rawDescription;
                }
                return TEXT[2] + rawDescription;
            case 1:
                if (card.hasTag(CustomTags.FLUX)) {
                    return TEXT[4] + rawDescription;
                }
                return TEXT[0] + rawDescription;
            case 2 :
                if (card.hasTag(CustomTags.FLUX)) {
                    return TEXT[5] + rawDescription;
                }
                return TEXT[1] + rawDescription;
        }
        return rawDescription;
    }
/*
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(Tooltip == null)
        {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_ID)));
            //Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_FORM_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_FORM_ID)));
        }
        return Tooltip;
    }*/

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int heat = CardTemperatureFields.getCardHeat(card);
        int amount = card instanceof MultiTempEffectCard ? ((MultiTempEffectCard) card).tempEffectAmount() : 1;
        Wiz.atb(new ModEngineTempAction(heat*amount));
        if (heat > 0 || (card.hasTag(CustomTags.FLUX) && heat < 0)) {
            Wiz.atb(new GainEnergyAction(amount));
            //action.exhaustCard = true;
            EvaporatePanelPatches.EvaporateField.evaporate.set(card, true);
            //Wiz.applyToSelf(new SteamPower(Wiz.adp(), 1));
        }
        if ((heat == 2 || (card.hasTag(CustomTags.FLUX) && heat == -2))&& !LoopcastField.LoopField.islooping.get(card)) {
            //Wiz.applyToSelf(new SteamPower(Wiz.adp(), 1));
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
        }
        if (heat == -2 || (card.hasTag(CustomTags.FLUX) && heat == 2)) {
            Wiz.atb(new CondenseRandomCardToDrawPileAction());
        }
        if (heat < 0 || (card.hasTag(CustomTags.FLUX) && heat > 0)) {
            Wiz.atb(new PullCardAction(amount));
        }
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        if (!card.isEthereal && (CardTemperatureFields.getCardHeat(card) < 0 || (card.hasTag(CustomTags.FLUX) && CardTemperatureFields.getCardHeat(card) > 0))) {
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
