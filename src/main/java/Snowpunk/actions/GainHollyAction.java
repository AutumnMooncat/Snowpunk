package Snowpunk.actions;

import Snowpunk.patches.HollyPatches;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.powers.interfaces.OnGainSnowPower;
import Snowpunk.powers.interfaces.OnUseSnowPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;

public class GainHollyAction extends AbstractGameAction {

    boolean silent;
    boolean isDouble;

    public GainHollyAction(boolean isDouble) {
        amount = 0;
        this.isDouble = isDouble;
    }

    public GainHollyAction(int amount) {
        this(amount, false);
    }

    public GainHollyAction(int amount, boolean silent) {
        this.silent = silent;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!silent)
            addToTop(new SFXAction("snowpunk:holly"));
        HollyPatches.Holly.amount += amount;
        isDone = true;
    }
}
