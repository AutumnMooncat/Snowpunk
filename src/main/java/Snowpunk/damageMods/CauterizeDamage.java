package Snowpunk.damageMods;

import Snowpunk.cardmods.ChillMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static Snowpunk.SnowpunkMod.makeID;

public class CauterizeDamage extends AbstractDamageModifier {

    public static final String ID = makeID(CauterizeDamage.class.getSimpleName());
    boolean isHot = false;

    public CauterizeDamage() {
        automaticBindingForCards = false;
    }

//    @Override
//    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
//        isHot = CardTemperatureFields.getExpectedCardHeatWhenPlayed(card) > 0;
//        return damage;
//    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {//&& isHot) {
            Wiz.att(new ApplyPowerAction(target, info.owner, new SingePower(target, lastDamageTaken)));
        }
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new CauterizeDamage();
    }
}
