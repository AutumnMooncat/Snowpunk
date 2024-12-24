package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GainBlockEqualToDebuffAction extends AbstractGameAction {
    String ID = null;

    public GainBlockEqualToDebuffAction(String ID, AbstractCreature target) {
        this.ID = ID;
        this.target = target;
    }

    @Override
    public void update() {
        if (ID != null) {
            AbstractPower debuff = target.getPower(ID);
            if (debuff != null && debuff.amount != 0)
                Wiz.att(new GainBlockAction(Wiz.adp(), Math.abs(debuff.amount)));
        }

        isDone = true;
    }
}
