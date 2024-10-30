package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GeneratorAction extends AbstractGameAction {

    AbstractCard card;

    public GeneratorAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.contains(card) ||
                AbstractDungeon.player.discardPile.contains(card) ||
                AbstractDungeon.player.drawPile.contains(card) ||
                AbstractDungeon.player.limbo.contains(card) ||
                AbstractDungeon.player.exhaustPile.contains(card) ||
                EvaporatePanel.evaporatePile.contains(card))
            addToTop(new GainEnergyAction(1));

        isDone = true;
    }
}
