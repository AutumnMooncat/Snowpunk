package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

import static Snowpunk.util.Wiz.atb;

public class DrawEvaporatedCardsAction extends AbstractGameAction {

    public DrawEvaporatedCardsAction(int numCards) {
        amount = numCards;
    }

    @Override
    public void update() {
        int numCards = Math.min(amount, EvaporatePanel.evaporatePile.size());
        if (numCards >= 1)
            atb(new DrawCardAction(numCards));
        isDone = true;
    }
}
