package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.cards.old_cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class GainBlockMod extends AbstractCardEffectMod {
    public GainBlockMod(String description, boolean secondVar) {
        super(description, secondVar);
        this.priority = -2;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((ARCHIVED_AssembledCard) card).secondBlock : card.block;
                Wiz.atb(new GainBlockAction(p, amount));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainBlockMod(description, useSecondVar);
    }
}
