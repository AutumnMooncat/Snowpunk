package Snowpunk.powers;

import Snowpunk.actions.CheckHandEmptyDrawAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class RailsPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(RailsPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public boolean drawn = false;

    public RailsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("blur");
    }

    @Override
    public void atStartOfTurnPostDraw() {
        drawn = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!drawn)
            addToBot(new CheckHandEmptyDrawAction(this));
    }

    @Override
    public void onDrawOrDiscard() {
        if (!drawn)
            addToBot(new CheckHandEmptyDrawAction(this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
