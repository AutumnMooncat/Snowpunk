package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.cards.old_cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class GainMEnergyMod extends AbstractCardEffectMod {
    public GainMEnergyMod(String description, boolean secondVar) {
        super(description, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((ARCHIVED_AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.atb(new GainEnergyAction(amount));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainMEnergyMod(description, useSecondVar);
    }
}
