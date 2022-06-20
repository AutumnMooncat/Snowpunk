package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class DrawCardMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public DrawCardMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
        super(name, description, type, rarity, target);
        this.effect = effect;
        this.upEffect = upEffect;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateMagic(card, effect, upEffect);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseConsumer((p, m) -> {
                if (useSecondVar) {
                    Wiz.atb(new DrawCardAction(((AssembledCard) card).secondMagic));
                } else {
                    Wiz.atb(new DrawCardAction(card.magicNumber));
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DrawCardMod(name, description, type, rarity, target, effect, upEffect);
    }
}
