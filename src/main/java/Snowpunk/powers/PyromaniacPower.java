package Snowpunk.powers;

import Snowpunk.actions.ExhaustSpecificCardAction;
import Snowpunk.actions.TryEmberForgeCopyAction;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class PyromaniacPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(PyromaniacPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public PyromaniacPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("blur");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        Wiz.att(new TryEmberForgeCopyAction(card, action, this));
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
        return new PyromaniacPower(owner, amount);
    }
}
