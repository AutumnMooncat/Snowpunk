package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class FreezeNextCardPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(FreezeNextCardPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public FreezeNextCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        //this.loadRegion("skillBurn");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && CardTemperatureFields.canModTemp(card, -1)) {
            flash();
            CardTemperatureFields.addHeat(card, -999);
            addToTop(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
