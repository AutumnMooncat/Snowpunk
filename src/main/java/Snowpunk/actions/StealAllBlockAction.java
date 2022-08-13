package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class StealAllBlockAction extends AbstractGameAction {
    private static final float DUR = 0.25F;

    public StealAllBlockAction(AbstractCreature target, AbstractCreature source) {
        this.setValues(target, source, this.amount);
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.duration = DUR;
    }

    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == DUR && this.target.currentBlock > 0) {
            Wiz.att(new GainBlockAction(source, target.currentBlock, true));
            this.target.loseBlock();
        }
        this.tickDuration();
    }
}
