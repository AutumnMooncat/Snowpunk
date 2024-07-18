package Snowpunk.actions;

import Snowpunk.patches.SnowballPatches;
import Snowpunk.powers.interfaces.OnGainSnowPower;
import Snowpunk.powers.interfaces.OnUseSnowPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

import static Snowpunk.util.Wiz.adp;

public class GainSnowballAction extends AbstractGameAction {

    boolean silent;
    boolean isDouble;

    public GainSnowballAction(boolean isDouble) {
        amount = 0;
        this.isDouble = isDouble;
    }

    public GainSnowballAction(int amount) {
        this(amount, false);
    }

    public GainSnowballAction(int amount, boolean silent) {
        this.silent = silent;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!silent) {
            Random rand = new Random();
            int num = rand.random(1, 5);
            addToTop(new SFXAction("snowpunk:snow" + num));
        }
        if (isDouble)
            SnowballPatches.Snowballs.changeSnow(SnowballPatches.Snowballs.getEffectiveAmount());
        else
            SnowballPatches.Snowballs.changeSnow(amount);
        if (amount > 0) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnGainSnowPower) {
                    p.flash();
                    ((OnGainSnowPower) p).onGainSnowball(amount);
                }
            }
        }
        if (amount < 0) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnUseSnowPower)
                    ((OnUseSnowPower) p).onUseSnowball(-amount);
            }
        }
        isDone = true;
    }
}
