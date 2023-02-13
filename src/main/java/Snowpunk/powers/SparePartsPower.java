package Snowpunk.powers;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HollyMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class SparePartsPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(SparePartsPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public SparePartsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("tools");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.baseDamage >= 0 || card.baseBlock >= 0 || CardModifierManager.hasModifier(card, GearMod.ID)) {
            CardModifierManager.addModifier(card, new GearMod(amount));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public float modifyBlock(float blockAmount) {
        if (blockAmount < 1)
            return blockAmount;
        return Math.max(blockAmount + amount, 0);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage + amount;
        return damage;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
