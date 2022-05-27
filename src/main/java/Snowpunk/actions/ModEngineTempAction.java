package Snowpunk.actions;

import Snowpunk.util.SteamEngine;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ModEngineTempAction extends AbstractGameAction {

    public ModEngineTempAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        SteamEngine.modifyHeat(amount);
        this.isDone = true;
    }
}
