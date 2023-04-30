package Snowpunk.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class WrenchPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(WrenchPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public WrenchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        isTurnBased = true;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}
