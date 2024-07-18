package Snowpunk.powers;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(BrassPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public BrassPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("tools");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.baseDamage >= 0 || card.baseBlock >= 0 || CardModifierManager.hasModifier(card, PlateMod.ID)) && !card.purgeOnUse) {
            CardModifierManager.addModifier(card, new PlateMod(amount));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
        }
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        int mult = 1;
        if (card instanceof GearMultCard)
            mult = ((GearMultCard) card).gearMult();
        if (blockAmount < 0)
            return blockAmount;
        return Math.max(blockAmount + amount * mult, 0);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        int mult = 1;
        if (card instanceof GearMultCard)
            mult = ((GearMultCard) card).gearMult();
        if (type == DamageInfo.DamageType.NORMAL)
            return damage + amount * mult;
        return damage;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BrassPower(owner, amount);
    }
}
