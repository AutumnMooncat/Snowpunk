package Snowpunk.util;

import Snowpunk.powers.EngineTempPower;
import Snowpunk.powers.SnowballPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HeatBasedEnergyManager extends EnergyManager {
    public int snowGain;

    public HeatBasedEnergyManager(int e) {
        super(e);
    }

    @Override
    public void prep() {
        SteamEngine.reset();
        calculateGains();
        gainSnow();
        super.prep();
        Wiz.applyToSelfTop(new EngineTempPower(Wiz.adp()));
    }

    @Override
    public void recharge() {
        calculateGains();
        gainSnow();
        super.recharge();
        SteamEngine.stabilize();
    }

    public void calculateGains() {
        energy = energyMaster + SteamEngine.getBonusEnergy();
        snowGain = SteamEngine.getSnowballs();
    }

    public void gainSnow() {
        if (snowGain > 0) {
            Wiz.applyToSelfTop(new SnowballPower(Wiz.adp(), snowGain));
        }
    }
}
