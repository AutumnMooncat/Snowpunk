package Snowpunk.util;

import Snowpunk.powers.EmberForgePowerOLD;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SnowballPower;
import com.megacrit.cardcrawl.core.EnergyManager;

public class HeatBasedEnergyManager extends EnergyManager {
    public int snowGain;
    public int fireGain;

    public HeatBasedEnergyManager(int e) {
        super(e);
    }

    @Override
    public void prep() {
        SteamEngine.reset();
        calculateGains();
        gainSnowAndSteam();
        super.prep();
        //Wiz.applyToSelfTop(new EngineTempPower(Wiz.adp()));
    }

    @Override
    public void recharge() {
        calculateGains();
        gainSnowAndSteam();
        super.recharge();
        SteamEngine.stabilize();
    }

    public void calculateGains() {
        energy = energyMaster + SteamEngine.getBonusEnergy();
        snowGain = SteamEngine.getSnowballs();
        fireGain = SteamEngine.getFire();

        if (Wiz.adp().hasPower(EmberForgePowerOLD.POWER_ID))
            energy = 0;
    }

    public void gainSnowAndSteam() {
        if (snowGain > 0) {
            Wiz.applyToSelfTop(new SnowballPower(Wiz.adp(), snowGain));
        }
        if (fireGain > 0) {
            Wiz.applyToSelfTop(new FireballPower(Wiz.adp(), fireGain));
        }
    }
}
