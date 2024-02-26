package Snowpunk.powers;

import Snowpunk.actions.CondenseAction;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class CarolingPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(CarolingPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static UIStrings carolStrings = CardCrawlGame.languagePack.getUIString(makeID("Carols"));
    public static String[] CAROLS = carolStrings.TEXT;

    public boolean hasPlayedAttack = false;

    public CarolingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        hasPlayedAttack = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            if (!hasPlayedAttack)
                flash();
            hasPlayedAttack = true;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (!hasPlayedAttack && amount > 1) {
                carol();
                flash();
                Wiz.atb(new GainBlockAction(owner, owner.currentBlock * amount));
            }
        }
    }

    public static void carol() {
        Random random = new Random();
        int speech = 1 + random.nextInt(CAROLS.length - 2);
        Wiz.atb(new TalkAction(true, CAROLS[speech], 2, 2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
