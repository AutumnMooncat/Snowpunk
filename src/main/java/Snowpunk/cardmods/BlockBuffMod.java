package Snowpunk.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class BlockBuffMod extends AbstractCardModifier {
    int amount;

    public BlockBuffMod(int amount) {
        this.amount = amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.baseBlock += amount;
        card.block += amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BlockBuffMod(amount);
    }
}
