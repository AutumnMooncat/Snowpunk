package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cards.parts.AbstractPartCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class TinkerAction extends AbstractGameAction {
    AbstractCard card;

    public TinkerAction(AbstractCard card) {
        this.card = card;
    }

    public TinkerAction() {
        this(null);
    }

    @Override
    public void update() {
        //If we have a pre-specified card, heat it
        if (card != null) {
            ArrayList<AbstractCard> validParts = new ArrayList<>();
            for (AbstractPartCard pc : SnowpunkMod.parts) {
                if (pc.getFilter().test(card)) {
                    validParts.add(pc);
                }
            }
            if (!validParts.isEmpty()) {
                Wiz.att(new SelectCardsAction(validParts, 1, "", false, card -> true, cards -> {
                    for (AbstractCard c : cards) {
                        if (c instanceof AbstractPartCard) {
                            ((AbstractPartCard) c).apply(card);
                        }
                    }
                }));
            }
        } else {
            HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
            ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
            for (AbstractCard c : Wiz.adp().hand.group) {
                AbstractCard copy = c.makeStatEquivalentCopy();
                cardMap.put(copy, c);
                selectionGroup.add(copy);
            }

            Wiz.att(new SelectCardsAction(selectionGroup, 1, "", false, card -> true, cards -> {
                for (AbstractCard c : cards) {
                    AbstractCard originalCard = cardMap.get(c);
                    ArrayList<AbstractCard> validParts = new ArrayList<>();
                    for (AbstractPartCard pc : SnowpunkMod.parts) {
                        if (pc.getFilter().test(originalCard)) {
                            validParts.add(pc);
                        }
                    }
                    if (!validParts.isEmpty()) {
                        Wiz.att(new SelectCardsAction(validParts, 1, "", false, card -> true, partCards -> {
                            for (AbstractCard pc : partCards) {
                                if (pc instanceof AbstractPartCard) {
                                    ((AbstractPartCard) pc).apply(originalCard);
                                }
                            }
                        }));
                    }
                }
            }));
        }
        this.isDone = true;
    }

    //TODO - Lifted directly, needs alteration
    private static AbstractCard.CardRarity rollRarity() {
        int roll = AbstractDungeon.cardRng.random(99);
        int rareRate = 3;
        if (roll < rareRate) {
            return AbstractCard.CardRarity.RARE;
        } else {
            return roll < 40 ? AbstractCard.CardRarity.UNCOMMON : AbstractCard.CardRarity.COMMON;
        }
    }
}
