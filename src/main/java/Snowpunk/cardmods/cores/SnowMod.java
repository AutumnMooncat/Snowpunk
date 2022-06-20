package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SnowMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public SnowMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
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
                Wiz.applyToSelf(new SnowballPower(Wiz.adp(), amount));
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SnowMod(name, description, type, rarity, target, effect, upEffect);
    }
}
