package Snowpunk.cardmods.cores;

import Snowpunk.actions.StealAllBlockAction;
import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class YoinkMod extends AbstractCardEffectMod {
    public YoinkMod(String description, boolean secondVar) {
        super(description, secondVar);
        this.priority = -3;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                Wiz.atb(new StealAllBlockAction(m, p));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new YoinkMod(description, useSecondVar);
    }
}
