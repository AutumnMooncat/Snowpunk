package Snowpunk.powers;

import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class PressureValvesPower extends AbstractEasyPower implements SnowAmountModifier, XCostModifier {
    public static String POWER_ID = makeID(PressureValvesPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public PressureValvesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("panache");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int modifySnow() {
        return amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PressureValvesPower(owner, amount);
    }

    @Override
    public int modifyX(AbstractCard abstractCard) {
        if (SnowballPatches.Snowballs.getTrueAmount() == 0 && !abstractCard.purgeOnUse)
            return amount;
        return 0;
    }
}
