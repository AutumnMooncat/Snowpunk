package Snowpunk.powers;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class DecoBotPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(DecoBotPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public DecoBotPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof ClankCard) {
            flash();
            Wiz.atb(new GainHollyAction(amount));
//            Wiz.att(new ApplyCardModifierAction(card, new PlateMod(amount)));
            Wiz.applyToSelf(new BrassPower(Wiz.adp(), amount));
        }
    }

//    @Override
//    public float modifyBlock(float blockAmount, AbstractCard card) {
//        if (blockAmount < 0 || !(card instanceof ClankCard))
//            return blockAmount;
//        return Math.max(blockAmount + amount, 0);
//    }
//
//    @Override
//    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
//        if (type == DamageInfo.DamageType.NORMAL && card instanceof ClankCard)
//            return damage + amount;
//        return damage;
//    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DecoBotPower(owner, amount);
    }

}
