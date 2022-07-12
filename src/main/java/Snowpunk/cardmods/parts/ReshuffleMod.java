package Snowpunk.cardmods.parts;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class ReshuffleMod extends AbstractCardModifier {
    public static final String ID = makeID(ReshuffleMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public ReshuffleMod() {
        this.priority = -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.shuffleBackIntoDrawPile = true;
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
/*
    @Override
    public boolean isInherent(AbstractCard card) {
        return inherent;
    }*/

    @Override
    public AbstractCardModifier makeCopy() {
        return new ReshuffleMod();
    }
}