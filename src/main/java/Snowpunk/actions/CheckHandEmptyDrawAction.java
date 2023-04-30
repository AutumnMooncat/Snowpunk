package Snowpunk.actions;

import Snowpunk.powers.RailsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

import static Snowpunk.util.Wiz.adp;

public class CheckHandEmptyDrawAction extends AbstractGameAction {

    RailsPower power;

    public CheckHandEmptyDrawAction(RailsPower power) {
        this.power = power;
    }

    @Override
    public void update() {
        if (Wiz.adp() != null && adp().hand.size() == 0) {
            addToTop(new DrawCardAction(power.amount));
            power.flash();
            power.drawn = true;
        }
        isDone = true;
    }
}
