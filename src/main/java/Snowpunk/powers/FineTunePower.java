package Snowpunk.powers;

import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class FineTunePower extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(FineTunePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public int numClanks = 0;

    public FineTunePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        numClanks = 0;
    }

    @Override
    public void atStartOfTurn() {
        numClanks = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof ClankCard && numClanks < amount) {
            Wiz.atb(new DrawCardAction(1));
            numClanks++;
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
    public boolean isFreeToPlay(AbstractCard card) {
        return card instanceof ClankCard && numClanks < amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new FineTunePower(owner, amount);
    }
}
