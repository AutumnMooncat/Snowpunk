package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class ApplySteamTrainPowerAction extends AbstractGameAction {
    ApplyPowerAction powerAction;
    AbstractGameAction appliedAction;
    AbstractGameAction negatedAction;

    public ApplySteamTrainPowerAction(ApplyPowerAction powerAction, AbstractGameAction appliedAction) {
        this(powerAction, appliedAction, null);
    }

    public ApplySteamTrainPowerAction(ApplyPowerAction powerAction, AbstractGameAction appliedAction, AbstractGameAction negatedAction) {
        this.powerAction = powerAction;
        this.appliedAction = appliedAction;
        this.negatedAction = negatedAction;
    }

    @Override
    public void update() {

        isDone = true;
    }
}
