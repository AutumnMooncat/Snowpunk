package Snowpunk.util;

import Snowpunk.TheConductor;
import Snowpunk.powers.EngineTempPower;
import Snowpunk.powers.interfaces.ModifyEngineSnowballsPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SteamEngine {
    public static final int FREEZING = -4;
    public static final int COLD = -2;
    public static final int STABLE = 0;
    public static final int HOT = 2;
    public static final int OVERHEATED = 4;
    public static final int STABILITY_FACTOR = 1;

    public static int heat;
    public static int stabilityPoint;

    public static void reset() {
        heat = 0;
        stabilityPoint = STABLE;
    }

    public static void modifyHeat(int amount) {
        heat += amount;
        if (heat > OVERHEATED) {
            heat = OVERHEATED;
        } else if (heat < FREEZING) {
            heat = FREEZING;
        }
        updatePowerString();
    }

    public static void modifyStabilityPoint(int amount) {
        stabilityPoint += amount;
        if (stabilityPoint > OVERHEATED) {
            stabilityPoint = OVERHEATED;
        } else if (stabilityPoint < FREEZING) {
            stabilityPoint = FREEZING;
        }
    }

    public static int getBonusEnergy() {
        if (heat == FREEZING) {
            return -2;
        } else if (heat <= COLD) {
            return -1;
        } else if (heat < HOT) {
            return 0;
        } else if (heat < OVERHEATED) {
            return 1;
        } else {
            return 2;
        }
    }

    public static int getSnowballs() {
        int bonus = 0;
        if ((Wiz.adp() instanceof TheConductor))
            bonus = 1;
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof ModifyEngineSnowballsPower) {
                bonus += ((ModifyEngineSnowballsPower) p).modifySnowballs(heat);
            }
        }

        if (heat == FREEZING)
            return 2 + bonus;
        if (heat <= COLD)
            return 1 + bonus;
        if (heat < HOT)
            return bonus;
        return 0;
    }

    public static int getFire() {
        if (heat == OVERHEATED) {
            return 2;
        } else if (heat >= HOT) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int getHeat() {
        return heat;
    }

    public static void stabilize() {
        if (Math.abs(heat - stabilityPoint) <= STABILITY_FACTOR) {
            heat = stabilityPoint;
        } else if (heat > stabilityPoint) {
            heat -= STABILITY_FACTOR;
        } else if (-heat > stabilityPoint) {
            heat += STABILITY_FACTOR;
        }
        updatePowerString();
    }

    public static void updatePowerString() {
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasPower(EngineTempPower.POWER_ID)) {
                AbstractDungeon.player.getPower(EngineTempPower.POWER_ID).updateDescription();
            }
        }
    }
}
