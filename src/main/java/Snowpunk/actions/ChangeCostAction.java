package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeCostAction extends AbstractGameAction {
    AbstractCard card;

    public ChangeCostAction(AbstractCard card, int cost) {
        this.card = card;
        this.amount = cost;
    }

    @Override
    public void update() {
        //Immediately return if trying to change a null card
        if (card == null) {
            this.isDone = true;
            return;
        }

        card.cost = card.costForTurn = amount;

        this.isDone = true;
    }
}
