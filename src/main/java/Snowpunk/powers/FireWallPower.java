package Snowpunk.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class FireWallPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(FireWallPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public FireWallPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("flameBarrier");
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (owner.currentBlock <= 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && amount > 0) {
            if (info.owner.hasPower(SingePower.POWER_ID)) {
                ((SingePower) info.owner.getPower(SingePower.POWER_ID)).keepThisTurn += amount;
                /*int bonusAmount = 0;
                if(owner.hasPower(BurningEmbersPower.POWER_ID))
                    bonusAmount = owner.getPower(BurningEmbersPower.POWER_ID).amount;*/
                addToTop(new ApplyPowerAction(info.owner, owner, new SingePower(info.owner, owner, amount), amount));
            } else {
                addToTop(new ApplyPowerAction(info.owner, owner, new SingePower(info.owner, owner, amount, amount), amount));
            }
            flash();
            if (damageAmount >= owner.currentBlock) {
                amount = 0;
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
