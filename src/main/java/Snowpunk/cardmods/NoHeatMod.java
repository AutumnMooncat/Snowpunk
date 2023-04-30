package Snowpunk.cardmods;

import Snowpunk.patches.CustomTags;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class NoHeatMod extends AbstractCardModifier {
    public static final String ID = makeID(NoHeatMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public NoHeatMod() {
        this.priority = 1;
    }

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
        return new NoHeatMod();
    }
}