package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class ChooChooPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(ChooChooPower.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public ChooChooPower(AbstractCreature owner, int num) {
        super(POWER_ID, NAME, PowerType.BUFF, true, owner, num);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        Wiz.atb(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return 0;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL || info.type == DamageInfo.DamageType.THORNS)
            return 0;
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];

    }
}
