package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class MoveCardToTopOfDrawPileAction extends AbstractGameAction {
    AbstractCard card;
    CardGroup from, to;

    public MoveCardToTopOfDrawPileAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.contains(card)) {
            AbstractDungeon.player.hand.removeCard(card);
//            Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(card)));
            //AbstractDungeon.player.hand.addToHand(card);
            Wiz.adp().hand.moveToDeck(card, false);
        }
        if (AbstractDungeon.player.drawPile.contains(card)) {
            AbstractDungeon.player.drawPile.removeCard(card);
//            Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(card)));
            //AbstractDungeon.player.hand.addToHand(card);
            Wiz.adp().drawPile.moveToDeck(card, false);
        }
        if (AbstractDungeon.player.discardPile.contains(card)) {
            AbstractDungeon.player.discardPile.removeCard(card);
//            Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(card)));
            Wiz.adp().discardPile.moveToDeck(card, false);
        }
        if (AbstractDungeon.player.exhaustPile.contains(card)) {
            AbstractDungeon.player.exhaustPile.removeCard(card);
            card.fadingOut = false;
            card.unfadeOut();
//            Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(card)));
            Wiz.adp().exhaustPile.moveToDeck(card, false);
        }
        if (EvaporatePanel.evaporatePile.contains(card)) {
            EvaporatePanel.evaporatePile.removeCard(card);
            card.fadingOut = false;
            card.unfadeOut();
//            Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(card)));
            EvaporatePanel.evaporatePile.moveToDeck(card, false);
        }

        isDone = true;
    }
}
