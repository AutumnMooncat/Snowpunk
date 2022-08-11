package Snowpunk.cardmods.cores;

import Snowpunk.actions.ApplyPowerActionWithFollowup;
import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ApplyAOETempStrDownMod extends AbstractCardEffectMod {
    public ApplyAOETempStrDownMod(String description, AbstractCoreCard.EffectTag type, int effect, int upEffect, boolean secondVar) {
        super(description, type, effect, upEffect, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.forAllMonstersLiving(mon -> Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(mon, p, new StrengthPower(mon, -amount)), new ApplyPowerAction(mon, p, new GainStrengthPower(mon, amount)))));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyAOETempStrDownMod(description, type, effect, upEffect, useSecondVar);
    }
}
