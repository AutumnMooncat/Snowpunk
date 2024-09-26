package Snowpunk.powers;

import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class GildedWrenchPower extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(GildedWrenchPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public GildedWrenchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof ClankCard) {
            Wiz.atb(new DrawCardAction(1));
            Wiz.atb(new ReducePowerAction(owner, owner, this, 1));
            flash();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
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
        return card instanceof ClankCard && amount > 0;
    }

    @Override
    public AbstractPower makeCopy() {
        return new GildedWrenchPower(owner, amount);
    }
}
