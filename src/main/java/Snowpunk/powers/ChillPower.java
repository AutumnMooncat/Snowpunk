package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class ChillPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ChillPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public int keepThisTurn;

    public ChillPower(AbstractCreature owner, int amount) {
        this(owner, amount, 0);
    }

    public ChillPower(AbstractCreature owner, int amount, int keepThisTurn) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        isTurnBased = true;
        this.keepThisTurn = keepThisTurn;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? Math.max(0, damage - this.amount) : damage;
    }

    @Override
    public void atEndOfRound() {
        if (keepThisTurn <= 0)
            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
        else {
            Wiz.atb(new ReducePowerAction(owner, owner, this, amount - keepThisTurn));
            keepThisTurn = 0;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
