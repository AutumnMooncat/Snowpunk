package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class ClockworkMod extends AbstractCardModifier {
    public static final String ID = makeID(ClockworkMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    int amount = 0;
    private static ArrayList<TooltipInfo> GearTip, Tooltip;

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

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (GearTip == null) {
            GearTip = new ArrayList<>();
            GearTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
        }

        if (Tooltip == null)
            Tooltip = new ArrayList<>();
        if (amount > 0)
            return GearTip;
        return Tooltip;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (group == Wiz.adp().hand) {
            amount++;
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