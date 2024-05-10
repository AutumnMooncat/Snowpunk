package Snowpunk.powers;

import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ChillPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ChillPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public ChillPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        isTurnBased = true;
    }

    @Override
    public void atStartOfTurn() {
        if (owner instanceof AbstractPlayer)
            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (type == DamageInfo.DamageType.NORMAL)
            return Math.max(0, damage - amount);
        return damage;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? Math.max(0, damage - this.amount) : damage;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!owner.hasPower(CharPower.POWER_ID) || !owner.hasPower(FrozenPower.POWER_ID))
            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChillPower(owner, amount);
    }
}
