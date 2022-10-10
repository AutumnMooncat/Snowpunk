package Snowpunk.powers;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
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
    public void atStartOfTurn() {
        addToBot(new LoseEnergyAction(amount));
        Wiz.applyToSelf(new FireballPower(owner, amount));
        flash();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
    }
}
