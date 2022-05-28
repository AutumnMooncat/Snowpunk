package Snowpunk.actions;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.BurningCoals;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;

public class ModCardTempAction extends AbstractGameAction {
    AbstractCard card;
    boolean random;
    int heat;

    public ModCardTempAction(AbstractCard card, int heat) {
        this.card = card;
        this.heat = heat;
    }

    public ModCardTempAction(int cardsToChoose, int heat, boolean random) {
        this.amount = cardsToChoose;
        this.heat = heat;
        this.random = random;
    }

    @Override
    public void update() {
        //Immediately return if trying to add 0 heat
        if (heat == 0) {
            this.isDone = true;
            return;
        }

        //If we have a pre-specified card, heat it
        if (card != null) {
            CardTemperatureFields.addHeat(card, heat);
        } else {
            //Assemble all valid cards based on if they can actually accept the change in heat
            CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : Wiz.adp().hand.group) {
                if (heat > 0 && CardTemperatureFields.getCardHeat(c) != 2) {
                    validCards.addToTop(c);
                } else if (heat < 0 && CardTemperatureFields.getCardHeat(c) != -2) {
                    validCards.addToTop(c);
                }
            }
            //If we have less valid cards than cards to modify, we can just hit all of them
            if (amount == -1 || amount >= validCards.size()) {
                for (AbstractCard c : validCards.group) {
                    CardTemperatureFields.addHeat(c, heat);
                }
            } else if (random) {
                //If not, and we are choosing randomly, remove them from the list as we go to not double up
                for (int i = 0 ; i < amount ; i++) {
                    AbstractCard c = validCards.getRandomCard(true);
                    validCards.removeCard(c);
                    CardTemperatureFields.addHeat(c, heat);
                }
            } else {
                //Failing all else, open a selection screen for the player using copied cards to not screw with the cards in hand
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();

                for (AbstractCard c : validCards.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
                }

                Wiz.att(new SelectCardsAction(selectionGroup, amount, "", false, card -> true, cards -> {
                    for (AbstractCard c : cards) {
                        CardTemperatureFields.addHeat(cardMap.get(c), heat);
                    }
                }));
            }
        }
        this.isDone = true;
    }
}
