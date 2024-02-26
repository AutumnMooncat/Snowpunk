package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.UUID;

public class RemoveFromExhaustPileAction extends AbstractGameAction {

    AbstractCard card;

    public RemoveFromExhaustPileAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        AbstractDungeon.player.exhaustPile.group.remove(card);
        isDone = true;
    }
}
