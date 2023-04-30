package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.util.Wiz.adp;

public class ExhaustSpecificCardAction extends AbstractGameAction {
    AbstractCard card;

    public ExhaustSpecificCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (Wiz.adp() != null) {
            if (adp().hand.contains(card))
                adp().hand.moveToExhaustPile(card);
            else if (adp().discardPile.contains(card))
                adp().discardPile.moveToExhaustPile(card);
            else if (adp().drawPile.contains(card))
                adp().drawPile.moveToExhaustPile(card);
            else if (adp().limbo.contains(card))
                adp().limbo.moveToExhaustPile(card);
            else card.triggerOnExhaust();
        }
        isDone = true;
    }
}
