package Snowpunk.powers;

import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.util.Wiz;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowballPower extends AbstractEasyPower implements XCostModifier {
    public static String POWER_ID = makeID(SnowballPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public SnowballPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int modifyX(AbstractCard abstractCard) {
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
        return amount;
    }

    @Override
    public boolean xCostModifierActive(AbstractCard c) {
        return !SCostFieldPatches.SCostField.isSCost.get(c);
    }

    @Override
    public void onRemove() {
        onConsumeSnow(amount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        onConsumeSnow(reduceAmount);
        super.reducePower(reduceAmount);
    }

    public void onConsumeSnow(int amount) {
        //TODO hooks go here
    }
}
