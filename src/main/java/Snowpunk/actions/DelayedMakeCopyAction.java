package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DelayedMakeCopyAction extends AbstractGameAction {

    AbstractCard card;
    int numCards;

    public DelayedMakeCopyAction(AbstractCard card, int delay, int numCards) {
        this.card = card;
        amount = delay;
        this.numCards = numCards;
    }

    @Override
    public void update() {
        if (amount <= 0) {
            for (int i = 0; i < numCards; i++)
                addToBot(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(), 1, true, true));
        } else
            addToBot(new DelayedMakeCopyAction(card, amount - 1, numCards));
        isDone = true;
    }
}