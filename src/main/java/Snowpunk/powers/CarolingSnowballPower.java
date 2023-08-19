package Snowpunk.powers;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class CarolingSnowballPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(CarolingSnowballPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static PowerStrings carolStrings = CardCrawlGame.languagePack.getPowerStrings(HolidayCheerPower.POWER_ID);
    public static String[] CAROL_DESCRIPTIONS = carolStrings.DESCRIPTIONS;

    private boolean prevTurnAttacked;

    public CarolingSnowballPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        prevTurnAttacked = false;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public void atStartOfTurn() {
        if (!prevTurnAttacked) {
            Wiz.atb(new GainSnowballAction((amount)));
            if (!owner.hasPower(CarolingPower.POWER_ID))
                CarolingPower.carol();
        }
        prevTurnAttacked = false;
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (card.type == AbstractCard.CardType.ATTACK)
                prevTurnAttacked = true;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK)
            prevTurnAttacked = true;
    }


}
