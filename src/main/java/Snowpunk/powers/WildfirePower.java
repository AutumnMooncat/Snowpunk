package Snowpunk.powers;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModEngineTempAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class WildfirePower extends AbstractEasyPower {
    public static String POWER_ID = makeID(WildfirePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public WildfirePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }

    @Override
    public void atEndOfTurn(boolean player) {
        flash();
        addToBot(new ModEngineTempAction(2 * amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
