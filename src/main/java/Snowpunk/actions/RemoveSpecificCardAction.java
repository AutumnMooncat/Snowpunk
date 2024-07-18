package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.util.Wiz.adp;

public class RemoveSpecificCardAction extends AbstractGameAction {
    AbstractCard card;

    public RemoveSpecificCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (Wiz.adp() != null) {
            if (adp().hand.contains(card))
                adp().hand.removeCard(card);
            else if (adp().discardPile.contains(card))
                adp().discardPile.moveToExhaustPile(card);
            else if (adp().drawPile.contains(card))
                adp().drawPile.moveToExhaustPile(card);
            else if (adp().limbo.contains(card))
                adp().limbo.moveToExhaustPile(card);
            else if (EvaporatePanel.evaporatePile.contains(card))
                EvaporatePanel.evaporatePile.moveToExhaustPile(card);
            else card.triggerOnExhaust();
        }
        isDone = true;
    }
}
