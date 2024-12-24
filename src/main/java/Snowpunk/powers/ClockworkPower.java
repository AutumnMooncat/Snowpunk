package Snowpunk.powers;

import Snowpunk.cardmods.GearMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ClockworkPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ClockworkPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public static int tick = 0;

    public ClockworkPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

//    @Override
//    public void atStartOfTurn() {
////        Wiz.applyToSelf(new PreventBrassConsumptionThisTurnPower(Wiz.adp(), amount));
//        Wiz.applyToSelf(new PreventBrassConsumptionPower(Wiz.adp(), amount));
//        if (tick % 2 == 0)
//            addToTop(new SFXAction("snowpunk:tick"));
//        else
//            addToTop(new SFXAction("snowpunk:tock"));
//        tick++;
//    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        int gears = GearMod.getGears(card);
        if (gears > 0) {
            flash();
            if (tick % 2 == 0)
                addToBot(new SFXAction("snowpunk:tick"));
            else
                addToBot(new SFXAction("snowpunk:tock"));
            tick++;
//            Wiz.applyToSelf(new BrassPower(Wiz.adp(), amount * gears));
            Wiz.atb(new DrawCardAction(amount));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ClockworkPower(owner, amount);
    }
}
