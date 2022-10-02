package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.cards.old_cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class ScavengeStrikeMod extends AbstractCardEffectMod {
    public ScavengeStrikeMod(String description, boolean secondVar) {
        super(description, secondVar);
        this.priority = -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((ARCHIVED_AssembledCard) card).secondDamage : card.damage;
                Wiz.atb(new DamageAction(m, new DamageInfo(p, amount, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ScavengeStrikeMod(description, useSecondVar);
    }
}
