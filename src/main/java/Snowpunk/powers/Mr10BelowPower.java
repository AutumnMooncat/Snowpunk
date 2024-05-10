package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Mr10BelowPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(Mr10BelowPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    boolean played = false;

    public Mr10BelowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("int");
        played = false;
    }

    @Override
    public void atStartOfTurn() {
        played = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && CardTemperatureFields.canModTemp(card, -1) &&
                CardTemperatureFields.getCardHeat(card) < 0 && !played &&
                !owner.hasPower(SteamPower.POWER_ID) && !owner.hasPower(FireburstPower.POWER_ID) &&
                !owner.hasPower(FireballPower.POWER_ID)) {
            flash();
            CardTemperatureFields.addHeat(card, -amount);
            played = true;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Mr10BelowPower(owner, amount);
    }
}
