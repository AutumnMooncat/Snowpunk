package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.AfterImagePower;

public class DealAOEDamageMod extends AbstractCardEffectMod {
    public DealAOEDamageMod(String description, AbstractCoreCard.ValueType type, int effect, int upEffect, boolean secondVar) {
        super(description, type, effect, upEffect, secondVar);
        this.priority = -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((AssembledCard) card).baseSecondDamage : card.baseDamage;
                Wiz.atb(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(amount), card.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealAOEDamageMod(description, type, effect, upEffect, useSecondVar);
    }
}
