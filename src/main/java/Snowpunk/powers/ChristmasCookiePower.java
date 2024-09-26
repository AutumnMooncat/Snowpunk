package Snowpunk.powers;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.OnCondensePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ChristmasCookiePower extends AbstractEasyPower implements OnCondensePower {
    public static String POWER_ID = makeID(ChristmasCookiePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public ChristmasCookiePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("attackBurn");
    }

    @Override
    public void onCondense(AbstractCard card) {
        if (amount > 0) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    CardTemperatureFields.addHeat(card, CardTemperatureFields.HOT);
                    isDone = true;
                }
            });
            //flash();
            amount--;
            if (amount == 0)
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChristmasCookiePower(owner, amount);
    }

}
