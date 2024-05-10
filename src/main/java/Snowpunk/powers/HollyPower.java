package Snowpunk.powers;

import Snowpunk.cardmods.GearMod;
import Snowpunk.powers.interfaces.OnCreateCardPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class HollyPower extends AbstractEasyPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID(HollyPower.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public HollyPower(AbstractCreature owner, int num) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, num);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
/*
    @Override
    public void onCreateCard(AbstractCard card) {
        CardModifierManager.addModifier(card, new GearMod(amount, true));
        card.superFlash();
        card.applyPowers();
    }*/

    @Override
    public void atStartOfTurn() {
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new HollyPower(owner, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target instanceof AbstractMonster && power.type == PowerType.DEBUFF) {
            Wiz.att(new RemoveSpecificPowerAction(owner, owner, this));
            flash();
            power.amount += amount;
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (target instanceof AbstractMonster && power.type == PowerType.DEBUFF) {
            Wiz.att(new RemoveSpecificPowerAction(owner, owner, this));
            flash();
            return stackAmount + amount;
        }
        return BetterOnApplyPowerPower.super.betterOnApplyPowerStacks(power, target, source, stackAmount);
    }
}
