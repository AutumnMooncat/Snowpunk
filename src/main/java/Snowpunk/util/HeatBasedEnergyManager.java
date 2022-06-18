package Snowpunk.util;

import Snowpunk.powers.EngineTempPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.SteamPower;
import com.megacrit.cardcrawl.core.EnergyManager;

public class HeatBasedEnergyManager extends EnergyManager {
    public int snowGain;
    public int steamGain;

    public HeatBasedEnergyManager(int e) {
        super(e);
    }

    @Override
    public void prep() {
        SteamEngine.reset();
        calculateGains();
        gainSnowAndSteam();
        super.prep();
        Wiz.applyToSelfTop(new EngineTempPower(Wiz.adp()));
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
        steamGain = SteamEngine.getSteam();
    }

    public void gainSnowAndSteam() {
        if (snowGain > 0) {
            Wiz.applyToSelfTop(new SnowballPower(Wiz.adp(), snowGain));
        }
        if (steamGain > 0) {
            Wiz.applyToSelfTop(new SteamPower(Wiz.adp(), steamGain));
        }
    }
}
