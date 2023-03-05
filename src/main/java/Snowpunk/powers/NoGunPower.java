package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class NoGunPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(NoGunPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public NoGunPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        isTurnBased = true;
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return card.type != AbstractCard.CardType.ATTACK;
    }

    @Override
    public void atStartOfTurn() {
        if (amount <= 1)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        else
            addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
