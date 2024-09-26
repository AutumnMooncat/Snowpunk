package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveCardToHandAction extends AbstractGameAction {
    AbstractCard card;
    CardGroup from, to;

    public MoveCardToHandAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.contains(card)) {
            isDone = true;
            return;
        }
        if (AbstractDungeon.player.drawPile.contains(card)) {
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.addToHand(card);
        }
        if (AbstractDungeon.player.discardPile.contains(card)) {
            AbstractDungeon.player.discardPile.removeCard(card);
            AbstractDungeon.player.hand.addToHand(card);
        }
        if (AbstractDungeon.player.exhaustPile.contains(card)) {
            AbstractDungeon.player.exhaustPile.removeCard(card);
            card.fadingOut = false;
            card.unfadeOut();
            AbstractDungeon.player.hand.addToHand(card);
        }
        if (EvaporatePanel.evaporatePile.contains(card)) {
            EvaporatePanel.evaporatePile.removeCard(card);
            card.fadingOut = false;
            card.unfadeOut();
            AbstractDungeon.player.hand.addToHand(card);
        }

        isDone = true;
    }
}
