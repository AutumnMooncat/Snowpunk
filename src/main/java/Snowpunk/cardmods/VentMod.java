package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.KeywordManager;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class VentMod extends AbstractCardModifier {
    public static final String ID = makeID(VentMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public VentMod() {
        this.priority = 1;
    }

    private static ArrayList<TooltipInfo> CondenseTip, Tooltip;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.VENT);
    }
/*
    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (CondenseTip == null) {
            CondenseTip = new ArrayList<>();
            CondenseTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.CONDENSE), BaseMod.getKeywordDescription(KeywordManager.CONDENSE)));
        }
        if (Tooltip == null)
            Tooltip = new ArrayList<>();

        if (!card.keywords.contains(KeywordManager.CONDENSE))
            return CondenseTip;

        return Tooltip;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[0];
    }
*/
    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new VentMod();
    }
}