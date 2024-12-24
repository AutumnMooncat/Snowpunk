package Snowpunk.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class OLDFrostbitePower extends AbstractEasyPower {// implements BetterOnApplyPowerPower {
    public static String POWER_ID = makeID(OLDFrostbitePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public OLDFrostbitePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

//    @Override
//    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
//        if (type == DamageInfo.DamageType.NORMAL)
//            return damage + amount;
//        return damage;
//    }


    /*
        @Override
        public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
            return true;
        }

        @Override
        public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
            if (stackAmount > 0 && power.ID == ChillPower.POWER_ID && !target.hasPower(ArtifactPower.POWER_ID)) {
                flash();
                power.flash();
                Wiz.atb(new LoseHPAction(target, source, stackAmount * amount));
                return stackAmount;
            }
            return stackAmount;
        }
    */
    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OLDFrostbitePower(owner, amount);
    }
}
