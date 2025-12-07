package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class MoveTopOfDrawPileToHandAction extends AbstractGameAction {
    AbstractCard card;
    CardGroup from, to;

    public MoveTopOfDrawPileToHandAction() {
    }

    @Override
    public void update() {

        if (AbstractDungeon.player.drawPile.size() > 0) {
            AbstractCard card = Wiz.adp().drawPile.getTopCard();
            AbstractDungeon.player.drawPile.removeCard(card);
            Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(card)));
        }

        isDone = true;
    }
}
