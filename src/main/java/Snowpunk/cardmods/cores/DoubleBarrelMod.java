package Snowpunk.cardmods.cores;

import Snowpunk.actions.ScatterDamageAction;
import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.cards.old_cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DoubleBarrelMod extends AbstractCardEffectMod {
    public DoubleBarrelMod(String description, boolean secondVar) {
        super(description, secondVar);
        this.priority = -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((ARCHIVED_AssembledCard) card).secondDamage : card.damage;
                Wiz.atb(new ScatterDamageAction(card, amount, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                Wiz.atb(new ScatterDamageAction(card, amount, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DoubleBarrelMod(description, useSecondVar);
    }
}
