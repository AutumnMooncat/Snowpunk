package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class BurnPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(BurnPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private final AbstractCreature source;

    public BurnPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        this.loadRegion("flameBarrier");
        this.source = source;
    }

    @Override
    public void atEndOfRound() {
        Wiz.atb(new DamageAction(owner, new DamageInfo(source, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        //TODO not hardcode this interaction?
        if (owner.hasPower(SootPower.POWER_ID)) {
            owner.getPower(SootPower.POWER_ID).flash();
            Wiz.atb(new ApplyPowerAction(owner, source, new BurnPower(owner, source, owner.getPower(SootPower.POWER_ID).amount)));
        } else {
            Wiz.atb(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
