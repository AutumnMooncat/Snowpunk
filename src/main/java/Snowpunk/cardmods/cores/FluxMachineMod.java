package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class FluxMachineMod extends AbstractCardEffectMod {
    public FluxMachineMod(String description, boolean secondVar) {
        super(description, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.atb(new DrawCardAction(amount, new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (AbstractCard c : DrawCardAction.drawnCards) {
                            if (c.cost >= 0) {
                                int newCost = AbstractDungeon.cardRandomRng.random(3);
                                if (c.cost != newCost) {
                                    c.cost = newCost;
                                    c.costForTurn = c.cost;
                                    c.isCostModified = true;
                                }
                                c.freeToPlayOnce = false;
                            }
                        }
                        this.isDone = true;
                    }
                }));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FluxMachineMod(description, useSecondVar);
    }
}
