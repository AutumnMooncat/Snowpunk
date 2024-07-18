package Snowpunk.damageMods;

import Snowpunk.actions.RemoveDamageModifierAction;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class TempSingeMod extends AbstractDamageModifier {

    private int amount;

    public TempSingeMod(int amount) {
        this.amount = amount;
    }


    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {
            Wiz.atb(new ApplyPowerAction(target, info.owner, new SingePower(target, amount)));
        }
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        Wiz.atb(new RemoveDamageModifierAction(card, this));
        return damage;
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new TempSingeMod(amount);
    }
}
