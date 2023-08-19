package Snowpunk.actions;

import Snowpunk.patches.SnowballPatches;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.random.Random;

import static Snowpunk.util.Wiz.adp;

public class GainSnowballAction extends AbstractGameAction {

    public GainSnowballAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        Random rand = new Random();
        int num = rand.random(1, 5);
        addToTop(new SFXAction("snowpunk:snow" + num));
        SnowballPatches.Snowballs.amount += amount;
        isDone = true;
    }
}
