package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.powers.SootPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ApplySootMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public ApplySootMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
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
                    Wiz.atb(new ApplyPowerAction(m, p, new SootPower(m, ((AssembledCard) card).secondMagic)));
                } else {
                    Wiz.atb(new ApplyPowerAction(m, p, new SootPower(m, card.magicNumber)));
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplySootMod(name, description, type, rarity, target, effect, upEffect);
    }
}
