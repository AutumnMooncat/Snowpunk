package Snowpunk.powers;

import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.patches.AltCostPatch;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.interfaces.OnUseSnowPower;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.util.Wiz;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class CarolingPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(CarolingPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public CarolingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        Wiz.atb(new HolidayCheerUpAction(amount));
    }
}
