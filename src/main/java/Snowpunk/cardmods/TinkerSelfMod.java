package Snowpunk.cardmods;

import Snowpunk.actions.TinkerAction;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class TinkerSelfMod extends AbstractCardModifier {
    public static final String ID = makeID(TinkerSelfMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public TinkerSelfMod() {
        this.priority = 2;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.exhaust = false;
        CardModifierManager.removeModifiersById(card, BetterExhaustMod.ID, false);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new TinkerAction(card));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[0];
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
        return new TinkerSelfMod();
    }
}