package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class EmberForgePowerOLD extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(EmberForgePowerOLD.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public EmberForgePowerOLD(AbstractCreature owner) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, -1);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        return CardTemperatureFields.getCardHeat(card) > 0;
    }
}
