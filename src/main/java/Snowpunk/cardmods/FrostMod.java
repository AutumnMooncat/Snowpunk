package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class FrostMod extends AbstractCardModifier {
    public static final String ID = makeID(FrostMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public FrostMod() {
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

/*
    @Override
    public void addCustomTooltips(AbstractCard card, List<TooltipInfo> list) {
        list.add(new TooltipInfo(BaseMod.getKeywordProper("Retain"), BaseMod.getKeywordDescription("Retain")));
        list.add(new TooltipInfo(TipHelper.capitalize(GameDictionary.VULNERABLE.NAMES[0]), GameDictionary.keywords.get(GameDictionary.VULNERABLE.NAMES[0])));
    }*/

    @Override
    public void onRetained(AbstractCard card) {
        Wiz.atb(new ReduceCostAction(card));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FrostMod();
    }
}