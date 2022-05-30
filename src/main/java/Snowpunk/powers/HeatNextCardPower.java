package Snowpunk.powers;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatNextCardPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(HeatNextCardPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public HeatNextCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("attackBurn");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        flash();
        Wiz.atb(new ModEngineTempAction(1));
        CardTemperatureFields.addHeat(card, 1);
        //addToTop(new ModCardTempAction(card, 1));
        addToTop(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}
