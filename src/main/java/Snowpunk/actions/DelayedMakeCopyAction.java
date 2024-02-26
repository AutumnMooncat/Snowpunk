package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DelayedMakeCopyAction extends AbstractGameAction {

    AbstractCard card;

    public DelayedMakeCopyAction(AbstractCard card, int delay) {
        this.card = card;
        amount = delay;
    }

    @Override
    public void update() {
        if (amount <= 0)
            addToBot(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(), 1, true, true));
        else
            addToBot(new DelayedMakeCopyAction(card, amount - 1));
        isDone = true;
    }
}