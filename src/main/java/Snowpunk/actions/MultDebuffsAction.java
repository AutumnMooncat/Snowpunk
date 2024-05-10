package Snowpunk.actions;

import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import java.util.Objects;

public class MultDebuffsAction extends AbstractGameAction {
    int mult;

    public MultDebuffsAction(AbstractMonster mo, int mult) {
        this.target = mo;
        this.mult = mult;
    }

    @Override
    public void update() {
        for (AbstractPower power : target.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF && power.amount != 0 && !Objects.equals(power.ID, GainStrengthPower.POWER_ID)) {
                power.flash();
                power.amount *= mult;
                power.updateDescription();
            }
        }
        isDone = true;
    }
}
