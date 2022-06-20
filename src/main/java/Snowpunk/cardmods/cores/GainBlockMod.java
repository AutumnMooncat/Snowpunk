package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class GainBlockMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public GainBlockMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
        super(name, description, type, rarity, target);
        this.effect = effect;
        this.upEffect = upEffect;
        this.priority = -2;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateBlock(card, effect, upEffect);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseConsumer((p, m) -> {
                if (useSecondVar) {
                    Wiz.atb(new GainBlockAction(p, ((AssembledCard) card).secondBlock));
                } else {
                    Wiz.atb(new GainBlockAction(p, card.block));
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainBlockMod(name, description, type, rarity, target, effect, upEffect);
    }
}
