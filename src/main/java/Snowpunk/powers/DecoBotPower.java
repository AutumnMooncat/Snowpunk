package Snowpunk.powers;

import Snowpunk.actions.GainHollyAction;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

//    @Override
//    public void atStartOfTurn() {
//        super.atStartOfTurn();
//        flash();
//        //Wiz.applyToSelf(new HollyPower(owner, amount));
//        Wiz.atb(new GainHollyAction(amount));
//    }

    //
//    @Override
//    public int onAttacked(DamageInfo info, int damageAmount) {
//        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != owner && damageAmount > 0) {
//            flash();
//            Wiz.applyToSelf(new HollyPower(owner, amount));
//        }
//        return damageAmount;
//    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof ClankCard) {
            flash();
            Wiz.atb(new GainHollyAction(amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DecoBotPower(owner, amount);
    }

}
