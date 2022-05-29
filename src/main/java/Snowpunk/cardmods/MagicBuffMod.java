package Snowpunk.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MagicBuffMod extends AbstractCardModifier {
    int amount;

    public MagicBuffMod(int amount) {
        this.amount = amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.baseMagicNumber += amount;
        card.magicNumber += amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MagicBuffMod(amount);
    }
}
