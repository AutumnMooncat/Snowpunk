package Snowpunk.powers;

import Snowpunk.cardmods.PlayCopyMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamFormPower extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(SteamFormPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    int playedThisTurn = 0;

    public SteamFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        playedThisTurn = 999;
    }

    @Override
    public void atStartOfTurn() {
//        Wiz.applyToSelf(new SteamPower(Wiz.adp(), amount));
        playedThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (playedThisTurn < amount && !card.purgeOnUse && !card.isInAutoplay)
            flash();

        playedThisTurn++;
    }

//        @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) == CardTemperatureFields.COLD)
//            Wiz.atb(new DrawCardAction(amount));
//        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) == CardTemperatureFields.HOT)
//            Wiz.atb(new GainEnergyAction(amount));
//    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SteamFormPower(owner, amount);
    }

    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        return playedThisTurn < amount;
    }
}
