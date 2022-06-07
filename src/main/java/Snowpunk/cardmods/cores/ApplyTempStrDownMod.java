package Snowpunk.cardmods.cores;

import Snowpunk.actions.ApplyPowerActionWithFollowup;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ApplyTempStrDownMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public ApplyTempStrDownMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
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
                    Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(m, p, new StrengthPower(m, -((AssembledCard) card).secondMagic)), new ApplyPowerAction(m, p, new GainStrengthPower(m, ((AssembledCard) card).secondMagic))));
                } else {
                    Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(m, p, new StrengthPower(m, -card.magicNumber)), new ApplyPowerAction(m, p, new GainStrengthPower(m, card.magicNumber))));
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyTempStrDownMod(name, description, type, rarity, target, effect, upEffect);
    }
}
