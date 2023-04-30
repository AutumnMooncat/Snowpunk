package Snowpunk.cards.abstracts;

import Snowpunk.powers.PermWrenchPower;
import Snowpunk.powers.WrenchPower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface ClankCard {

    static boolean tryClank(AbstractCard card) {
        if (Wiz.adp() != null && Wiz.adp().hasPower(WrenchPower.POWER_ID) && (Wiz.adp().getPower(WrenchPower.POWER_ID).amount > 0)) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            Wiz.adp().getPower(WrenchPower.POWER_ID).amount--;
            if (Wiz.adp().getPower(WrenchPower.POWER_ID).amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), Wiz.adp().getPower(WrenchPower.POWER_ID)));
            return false;
        }
        if (Wiz.adp() != null && Wiz.adp().hasPower(PermWrenchPower.POWER_ID) && (Wiz.adp().getPower(PermWrenchPower.POWER_ID).amount > 0)) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            Wiz.adp().getPower(PermWrenchPower.POWER_ID).amount--;
            if (Wiz.adp().getPower(PermWrenchPower.POWER_ID).amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), Wiz.adp().getPower(PermWrenchPower.POWER_ID)));
            return false;
        }
        return true;
    }

    public abstract void onClank();
}
