package Snowpunk.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class IcePower extends AbstractEasyPower {
    public static String POWER_ID = makeID(IcePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public IcePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, true, owner, amount);
        this.loadRegion("channel");
    }

    @Override
    public void atEndOfRound() {
        addToTop(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return damage * 0.5f;
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
