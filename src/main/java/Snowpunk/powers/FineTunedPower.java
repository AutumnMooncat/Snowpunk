package Snowpunk.powers;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class FineTunedPower extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(FineTunedPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public FineTunedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof ClankCard || CardModifierManager.hasModifier(card, GearMod.ID) || CardModifierManager.hasModifier(card, PlateMod.ID) ||
                CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) != 0 || CardModifierManager.hasModifier(card, HatMod.ID)) {
            //Wiz.atb(new DrawCardAction(1));
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
        if (amount <= 0)
            return false;
        if (card instanceof ClankCard)
            return true;
        if (CardModifierManager.hasModifier(card, GearMod.ID) && ((GearMod) CardModifierManager.getModifiers(card, GearMod.ID).get(0)).amount > 0)
            return true;
        if (CardModifierManager.hasModifier(card, PlateMod.ID) && ((PlateMod) CardModifierManager.getModifiers(card, PlateMod.ID).get(0)).amount > 0)
            return true;
        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) != 0)
            return true;
        if ((card.baseBlock >= 0 || card.baseDamage >= 0) && owner.hasPower(BrassPower.POWER_ID))
            return true;
        return false;
    }

    @Override
    public AbstractPower makeCopy() {
        return new FineTunedPower(owner, amount);
    }
}
