package Snowpunk.actions;

import Snowpunk.powers.ChillPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ConvertSingeToChillAction extends AbstractGameAction {

    public ConvertSingeToChillAction(AbstractMonster target) {
        this.target = target;
    }

    @Override
    public void update() {
        int singe = 0;
        if (target.hasPower(SingePower.POWER_ID))
            singe = target.getPower(SingePower.POWER_ID).amount;
        if (singe > 0) {
            Wiz.att(new RemoveSpecificPowerAction(target, AbstractDungeon.player, SingePower.POWER_ID));
            Wiz.att(new ApplyPowerAction(target, AbstractDungeon.player, new ChillPower(target, singe), singe));
        }
        isDone = true;
    }
}
