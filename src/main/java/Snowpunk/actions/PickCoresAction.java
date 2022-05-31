package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.interfaces.OnTinkeredCard;
import Snowpunk.cards.parts.AbstractPartCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.ArrayList;

class PickCoresAction extends AbstractGameAction {
    public AbstractCard cardToPickFor;
    boolean random;

    public PickCoresAction(AbstractCard card) {
        this(card, false);
    }

    public PickCoresAction(AbstractCard card, boolean random) {
        this.cardToPickFor = card;
        this.random = random;
    }

    @Override
    public void update() {
        if (random) {
            giveRandomCore(cardToPickFor);
        } else {
            CardGroup validCores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCoreCard cc : SnowpunkMod.cores) {
                if (AssembleCardAction.pickedCores.stream().noneMatch(c -> c.getClass().equals(cc.getClass()))) {
                    AbstractCoreCard copy = (AbstractCoreCard) cc.makeCopy();
                    if (!AssembleCardAction.pickedCores.isEmpty()) {
                        copy.prepMultiCoreDownside();
                    }
                    validCores.addToTop(copy);
                }
            }
            if (!validCores.isEmpty()) {
                ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
                if (validCores.size() <= 3) {
                    cardsToPick.addAll(validCores.group);
                } else {
                    for (int i = 0; i < 3; i++) {
                        AbstractCard c = validCores.getRandomCard(true);
                        validCores.removeCard(c);
                        cardsToPick.add(c);
                    }
                }
                Wiz.att(new SelectCardsCenteredAction(cardsToPick, 1, "", false, crd -> true, cards -> {
                    for (AbstractCard c : cards) {
                        if (c instanceof AbstractCoreCard) {
                            ((AbstractCoreCard) c).apply(cardToPickFor);
                            validCores.removeCard(c);
                            AssembleCardAction.pickedCores.add((AbstractCoreCard) c);
                        }
                    }
                }));
            }
        }
        this.isDone = true;
    }

    private static void giveRandomCore(AbstractCard card) {
        CardGroup validCores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCoreCard cc : SnowpunkMod.cores) {
            if (AssembleCardAction.pickedCores.stream().noneMatch(c -> c.getClass().equals(cc.getClass()))) {
                AbstractCoreCard copy = (AbstractCoreCard) cc.makeCopy();
                if (!AssembleCardAction.pickedCores.isEmpty()) {
                    copy.prepMultiCoreDownside();
                }
                validCores.addToTop(copy);
            }
        }
        if (!validCores.isEmpty()) {
            AbstractCard core = validCores.getRandomCard(true);
            if (core instanceof AbstractCoreCard) {
                ((AbstractCoreCard) core).apply(card);
                validCores.removeCard(core);
                AssembleCardAction.pickedCores.add((AbstractCoreCard) core);
            }
        }
    }
}
