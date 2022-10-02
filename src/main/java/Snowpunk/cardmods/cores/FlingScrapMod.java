package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.cards.old_cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class FlingScrapMod extends AbstractCardEffectMod {
    public FlingScrapMod(String description, boolean secondVar) {
        super(description, secondVar);
        this.priority = -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((ARCHIVED_AssembledCard) card).baseSecondDamage : card.baseDamage;
                Wiz.atb(new SFXAction("ATTACK_HEAVY"));
                Wiz.atb(new VFXAction(p, new CleaveEffect(), 0.1F));
                Wiz.atb(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(amount), card.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FlingScrapMod(description, useSecondVar);
    }
}
