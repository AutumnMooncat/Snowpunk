package Snowpunk.actions;

import Snowpunk.util.SteamEngine;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ModEngineTempAction extends AbstractGameAction {

    boolean stabilize = false;


    public ModEngineTempAction(int amount) {
        this.amount = amount;
    }

    public ModEngineTempAction(boolean stabilize) {
        this.stabilize = stabilize;
    }

    @Override
    public void update() {
        if (stabilize)
            SteamEngine.modifyHeat(-SteamEngine.getHeat());
        else
            SteamEngine.modifyHeat(amount);
        this.isDone = true;
    }
}
