package Snowpunk.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class SootPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(SootPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public SootPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        this.loadRegion("fumes");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
