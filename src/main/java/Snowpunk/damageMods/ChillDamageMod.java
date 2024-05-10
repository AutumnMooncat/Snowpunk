package Snowpunk.damageMods;

import Snowpunk.powers.ChillPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ChillDamageMod extends AbstractDamageModifier {
    public int amount;
    boolean inherent;

    public ChillDamageMod(int amount) {
        this.amount = amount;
    }

    public ChillDamageMod(int amount, boolean inherent) {
        this.amount = amount;
        this.inherent = inherent;
    }

    @Override
    public boolean isInherent() {
        return inherent;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        Wiz.atb(new ApplyPowerAction(target, info.owner, new ChillPower(target, amount)));
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new ChillDamageMod(amount, inherent);
    }
}
