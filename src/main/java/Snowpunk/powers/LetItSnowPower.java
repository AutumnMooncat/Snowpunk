package Snowpunk.powers;

import Snowpunk.actions.CondenseAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.powers.interfaces.OnGainSnowPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class LetItSnowPower extends AbstractEasyPower implements OnGainSnowPower {
    public static String POWER_ID = makeID(LetItSnowPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public LetItSnowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void onGainSnowball(int snow) {
        if (amount > 0)
            addToBot(new CondenseAction(amount));
    }
}
