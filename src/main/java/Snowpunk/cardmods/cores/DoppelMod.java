package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class DoppelMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public DoppelMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
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
                    Wiz.applyToSelf(new DrawCardNextTurnPower(p, ((AssembledCard) card).secondMagic));
                } else {
                    Wiz.applyToSelf(new DrawCardNextTurnPower(p, card.magicNumber));
                }
                Wiz.applyToSelf(new EnergizedPower(p, 1));
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DoppelMod(name, description, type, rarity, target, effect, upEffect);
    }
}
