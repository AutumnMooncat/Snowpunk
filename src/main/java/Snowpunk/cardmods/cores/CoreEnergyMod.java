package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CoreEnergyMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public CoreEnergyMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
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
                int amount = useSecondVar ? ((AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.atb(new GainEnergyAction(amount));
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CoreEnergyMod(name, description, type, rarity, target, effect, upEffect);
    }
}
