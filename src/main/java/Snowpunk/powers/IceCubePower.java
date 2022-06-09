package Snowpunk.powers;

import Snowpunk.powers.interfaces.OnUseSnowPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCubePower extends AbstractEasyPower implements OnUseSnowPower {
    public static String POWER_ID = makeID(IceCubePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public IceCubePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseSnowball(int snow) {
        flash();
        Wiz.atb(new GainBlockAction(owner, snow*amount));
    }
}
