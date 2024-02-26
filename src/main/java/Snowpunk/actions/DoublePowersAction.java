package Snowpunk.actions;

import Snowpunk.patches.WasPowerActuallyAppliedPatches;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DoublePowersAction extends AbstractGameAction {
    AbstractPower.PowerType type;

    public DoublePowersAction(AbstractCreature target, AbstractPower.PowerType type) {
        this.type = type;
        this.target = target;
    }

    @Override
    public void update() {
        for (AbstractPower power : target.powers) {
            if (power.type == type)
                Wiz.att(new ApplyPowerAction(target, target, power));
        }

        isDone = true;
    }
}
