package Snowpunk.damageMods;

import Snowpunk.powers.BrassPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BrassDamage extends AbstractDamageModifier {

    public BrassDamage() {

    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0)
            Wiz.applyToSelf(new BrassPower(Wiz.adp(), lastDamageTaken));
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BrassDamage();
    }
}
