package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class DrawNextTurnMod extends AbstractCardEffectMod {
    public DrawNextTurnMod(String description, AbstractCoreCard.EffectTag type, int effect, int upEffect, boolean secondVar) {
        super(description, type, effect, upEffect, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.applyToSelf(new EnergizedPower(p, 1));
                Wiz.applyToSelf(new DrawCardNextTurnPower(p, amount));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DrawNextTurnMod(description, type, effect, upEffect, useSecondVar);
    }
}
