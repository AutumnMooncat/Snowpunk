package Snowpunk.powers;

import Snowpunk.cardmods.GearMod;
import Snowpunk.powers.interfaces.OnCreateCardPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class HotEnergyPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(HotEnergyPower.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public HotEnergyPower(AbstractCreature owner, int num) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, num);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        Wiz.atb(new GainEnergyAction(amount));
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new HotEnergyPower(owner, amount);
    }
}
