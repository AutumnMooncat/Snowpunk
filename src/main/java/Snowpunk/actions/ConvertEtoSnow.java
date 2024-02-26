package Snowpunk.actions;

import Snowpunk.patches.SnowballPatches;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ConvertEtoSnow extends AbstractGameAction {

    public ConvertEtoSnow() {
        this(1);
    }

    public ConvertEtoSnow(int mult) {
        amount = mult;
    }

    @Override
    public void update() {
        int e = EnergyPanel.getCurrentEnergy();
        Wiz.att(new GainSnowballAction(e * amount));
        Wiz.att(new LoseEnergyAction(e));
        isDone = true;
    }
}
