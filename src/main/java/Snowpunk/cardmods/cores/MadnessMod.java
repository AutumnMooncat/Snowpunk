package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MadnessMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public MadnessMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
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
                for (int i = 0 ; i < amount ; i++) {
                    Wiz.atb(new MadnessAction());
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MadnessMod(name, description, type, rarity, target, effect, upEffect);
    }
}
