package Snowpunk.actions;

import Snowpunk.relics.ChristmasSpirit;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

import static Snowpunk.util.Wiz.adp;

public class PullCardAction extends AbstractGameAction {

    public PullCardAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        Wiz.att(new DrawCardAction(Math.min(amount, adp().drawPile.size())));
        isDone = true;
    }
}
