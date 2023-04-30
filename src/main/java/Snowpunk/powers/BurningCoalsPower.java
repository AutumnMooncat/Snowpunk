package Snowpunk.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class BurningCoalsPower extends AbstractEasyPower implements BetterOnApplyPowerPower {
    public static String POWER_ID = makeID(BurningCoalsPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public BurningCoalsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, true, owner, amount);
    }
/*
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(SingePower.POWER_ID) && source == owner && target != owner && !target.hasPower("Artifact")) {
            flash();
            power.amount += amount;
            power.updateDescription();
        }
    }*/

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.amount > 0 && source == owner && power.ID == SingePower.POWER_ID && !target.hasPower("Artifact")) {
            flash();
            power.amount += amount;
            power.updateDescription();
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.amount > 0 && source == owner && power.ID == SingePower.POWER_ID) {
            flash();
            //power.amount += amount;
            power.updateDescription();
            return stackAmount + amount;
        }
        return stackAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
