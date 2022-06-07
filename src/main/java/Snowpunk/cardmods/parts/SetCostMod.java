package Snowpunk.cardmods.parts;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SetCostMod extends AbstractCardModifier {
    int amount;

    public SetCostMod(int amount) {
        this.amount = amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = card.costForTurn = amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SetCostMod(amount);
    }
}
