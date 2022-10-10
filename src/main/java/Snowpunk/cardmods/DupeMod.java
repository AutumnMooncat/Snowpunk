package Snowpunk.cardmods;

import Snowpunk.actions.TinkerAction;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class DupeMod extends AbstractCardModifier {
    public static final String ID = makeID(DupeMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    int amount = 0;

    public DupeMod() {
        this(1);
    }

    public DupeMod(int num) {
        this.priority = 2;
        amount = num;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new MakeTempCardInDiscardAction(card.makeStatEquivalentCopy(), amount));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.equals("") || rawDescription.endsWith(" NL "))
            return rawDescription + TEXT[0];
        return rawDescription + TEXT[1] + TEXT[0];
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
        return new DupeMod(amount);
    }
}