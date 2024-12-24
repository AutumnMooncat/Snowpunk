package Snowpunk.actions;

import Snowpunk.cardmods.*;
import Snowpunk.cards.Cryogenizer;
import Snowpunk.cards.Juggle;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class MoveModifiersAction extends AbstractGameAction {

    public static final String ID = makeID(MoveModifiersAction.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(Juggle.ID).EXTENDED_DESCRIPTION;

    ArrayList<AbstractCard> modCards, nonModCards, chosenCards;

    int step = 1;
    boolean copy;

    public MoveModifiersAction(int numCards, boolean copy) {
        this.actionType = ActionType.CARD_MANIPULATION;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;

        modCards = new ArrayList<>();
        nonModCards = new ArrayList<>();
        chosenCards = new ArrayList<>();
        amount = numCards;
        step = 1;
        this.copy = copy;
    }

    public void update() {
        if (duration == startDuration && step == 1) {
            for (AbstractCard c : Wiz.adp().hand.group) {
                if (CardModifierManager.hasModifier(c, GearMod.ID) || CardModifierManager.hasModifier(c, PlateMod.ID) ||
                        CardTemperatureFields.getCardHeat(c) != 0 || CardModifierManager.hasModifier(c, HatMod.ID))
                    modCards.add(c);
                else
                    nonModCards.add(c);
            }

            Wiz.adp().hand.group.removeAll(nonModCards);

            if (Wiz.adp().hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            }

            if (Wiz.adp().hand.size() <= 1) {
                chosenCards.add(Wiz.adp().hand.getTopCard());
                Wiz.adp().hand.removeCard(Wiz.adp().hand.getTopCard());
                returnCards();
                step = 2;
                duration = startDuration;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, true, false, false, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && step == 1) {
            chosenCards.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
            step = 2;
            duration = startDuration;
        }

        if (duration == startDuration && step == 2 && chosenCards.size() > 0) {

            if (Wiz.adp().hand.size() == 0) {
                isDone = true;
                return;
            }

            if (Wiz.adp().hand.size() == 1) {
                moveTo(AbstractDungeon.player.hand.getTopCard());
                isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[1], 1, false, false, false, false, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && step == 2) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                moveTo(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : nonModCards)
            Wiz.adp().hand.addToTop(c);

        Wiz.adp().hand.refreshHandLayout();
    }

    private void moveTo(AbstractCard card) {
        for (AbstractCard cardFrom : chosenCards) {
            moveMods(cardFrom, card);
            AbstractDungeon.player.hand.addToTop(cardFrom);
        }
    }

    private void moveMods(AbstractCard cardFrom, AbstractCard cardTo) {
        if (CardModifierManager.hasModifier(cardFrom, GearMod.ID)) {
            int amount = ((GearMod) CardModifierManager.getModifiers(cardFrom, GearMod.ID).get(0)).amount;
            if (!copy)
                CardModifierManager.addModifier(cardFrom, new GearMod(-amount));
            CardModifierManager.addModifier(cardTo, new GearMod(amount));
        }
        if (CardModifierManager.hasModifier(cardFrom, PlateMod.ID)) {
            int amount = ((PlateMod) CardModifierManager.getModifiers(cardFrom, PlateMod.ID).get(0)).amount;
            if (!copy)
                CardModifierManager.addModifier(cardFrom, new PlateMod(-amount));
            CardModifierManager.addModifier(cardTo, new PlateMod(amount));
        }
        if (CardModifierManager.hasModifier(cardFrom, HatMod.ID)) {
            int amount = ((HatMod) CardModifierManager.getModifiers(cardFrom, HatMod.ID).get(0)).amount;
            if (!copy)
                CardModifierManager.addModifier(cardFrom, new HatMod(-amount));
            CardModifierManager.addModifier(cardTo, new HatMod(amount));
        }
        if (CardModifierManager.hasModifier(cardFrom, TemperatureMod.ID)) {
            CardTemperatureFields.addHeat(cardTo, CardTemperatureFields.getCardHeat(cardFrom));
            while (CardTemperatureFields.getCardHeat(cardFrom) != 0 && !copy)
                CardTemperatureFields.reduceTemp(cardFrom);
        }
        cardTo.superFlash();
        cardTo.applyPowers();
    }
}
