package Snowpunk.powers;

import Snowpunk.actions.CondenseAction;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class CarolingPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(CarolingPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static PowerStrings carolStrings = CardCrawlGame.languagePack.getPowerStrings(HolidayCheerPower.POWER_ID);
    public static String[] CAROL_DESCRIPTIONS = carolStrings.DESCRIPTIONS;

    public CarolingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (int i = 0; i < amount; i++)
            addToBot(new CondenseAction(true));

        carol();
        flash();
    }

    public static void carol() {
        Random random = new Random();
        int speech = 1 + random.nextInt(CAROL_DESCRIPTIONS.length - 2);
        Wiz.atb(new TalkAction(true, CAROL_DESCRIPTIONS[speech], 2, 2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }
}
