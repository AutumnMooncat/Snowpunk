package Snowpunk.powers;

import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static Snowpunk.SnowpunkMod.makeID;

public class FrostbitePower extends AbstractEasyPower {// implements BetterOnApplyPowerPower {
    public static String POWER_ID = makeID(FrostbitePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public FrostbitePower(AbstractCreature owner, int amount) {
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
        return new FrostbitePower(owner, amount);
    }
}
