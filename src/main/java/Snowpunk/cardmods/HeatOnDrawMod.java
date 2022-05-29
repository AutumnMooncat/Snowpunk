package Snowpunk.cardmods;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatOnDrawMod extends AbstractCardModifier {
    public static final String ID = makeID(HeatOnDrawMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public HeatOnDrawMod() {}

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[0];
    }

    @Override
    public void onDrawn(AbstractCard card) {
        Wiz.atb(new ModCardTempAction(card, 1));
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
        return new HeatOnDrawMod();
    }
}