package Snowpunk.cardmods.cores;

import Snowpunk.actions.ApplyPowerActionWithFollowup;
import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.cards.old_cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ApplyTempStrDownMod extends AbstractCardEffectMod {
    public ApplyTempStrDownMod(String description, boolean secondVar) {
        super(description, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((ARCHIVED_AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(m, p, new StrengthPower(m, -amount)), new ApplyPowerAction(m, p, new GainStrengthPower(m, amount))));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyTempStrDownMod(description, useSecondVar);
    }
}
