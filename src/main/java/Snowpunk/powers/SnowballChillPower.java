package Snowpunk.powers;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowballChillPower extends AbstractEasyPower
{
    public static String POWER_ID = makeID(SnowballChillPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public SnowballChillPower(AbstractCreature owner, int amount)
    {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }
}
