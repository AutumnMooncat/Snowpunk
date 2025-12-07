package Snowpunk.actions;

import Snowpunk.patches.HollyPatches;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MiserAction extends AbstractGameAction {
    public MiserAction(AbstractMonster monster) {
        target = monster;
    }

    @Override
    public void update() {
        int holly = HollyPatches.Holly.amount;
        Wiz.applyToEnemy((AbstractMonster) target, new SingePower(target, holly));
        Wiz.applyToEnemy((AbstractMonster) target, new ChillPower(target, holly));
        this.isDone = true;
    }
}
