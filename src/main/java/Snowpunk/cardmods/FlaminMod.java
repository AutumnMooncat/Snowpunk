package Snowpunk.cardmods;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class FlaminMod extends AbstractCardModifier {
    public static final String ID = makeID(FlaminMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public FlaminMod() {
        this.priority = 1;
    }


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (CardTemperatureFields.getCardHeat(card) >= CardTemperatureFields.HOT)
            return modID.toLowerCase() + ":" + BaseMod.getKeywordProper(KeywordManager.FLAMIN) + " " +
                    modID.toLowerCase() + ":" + BaseMod.getKeywordProper(KeywordManager.HOT) + ". NL " + rawDescription;
        return modID.toLowerCase() + ":" + BaseMod.getKeywordProper(KeywordManager.FLAMIN) + ". NL " + rawDescription;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        if (CardTemperatureFields.getCardHeat(card) < CardTemperatureFields.HOT)
            Wiz.atb(new ModCardTempAction(card, 1));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FlaminMod();
    }
}