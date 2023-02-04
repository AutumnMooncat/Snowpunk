package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class ClockworkMod extends AbstractCardModifier {
    public static final String ID = makeID(ClockworkMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public ClockworkMod() {
        priority = 1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.CLOCKWORK);
        CardModifierManager.addModifier(card, new PrefixManager());
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(CustomTags.CLOCKWORK);
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
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (group == Wiz.adp().hand) {
            Wiz.atb(new ReduceCostAction(card));
        }
    }
/*
    @Override
    public void onRetained(AbstractCard card) {
        Wiz.atb(new ReduceCostAction(card));
    }*/

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ClockworkMod();
    }
}