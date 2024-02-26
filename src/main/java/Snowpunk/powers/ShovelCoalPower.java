package Snowpunk.powers;

import Snowpunk.actions.CondenseRandomCardToHandAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ShovelCoalPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ShovelCoalPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    int numHot = 0;

    public ShovelCoalPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
        numHot = 0;
    }

    @Override
    public void atStartOfTurn() {
        numHot = 0;
        flash();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) == CardTemperatureFields.HOT && numHot < amount) {
            numHot++;
            Wiz.atb(new CondenseRandomCardToHandAction(1));
            flash();
        }
        super.onPlayCard(card, m);
    }

    @Override
    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[0] : (DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]);
    }
}
