package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DelayedMakeCopyAction extends AbstractGameAction {

    AbstractCard card;

    public DelayedMakeCopyAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        addToBot(new MakeTempCardInDrawPileAction(card.makeStatEquivalentCopy(), 1, true, true));
        isDone = true;
    }
}