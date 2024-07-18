package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamFormPower extends AbstractEasyPower implements NonStackablePower {
    public static String POWER_ID = makeID(SteamFormPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public SteamFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("blur");
    }

    @Override
    public void atStartOfTurn() {
        Wiz.applyToSelf(new SteamPower(Wiz.adp(), amount));
    }

    //    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) == CardTemperatureFields.COLD)
//            Wiz.atb(new DrawCardAction(amount));
//        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) == CardTemperatureFields.HOT)
//            Wiz.atb(new GainEnergyAction(amount));
//    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SteamFormPower(owner, amount);
    }
}
