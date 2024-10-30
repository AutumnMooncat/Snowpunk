package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class IncreaseColdAction extends AbstractGameAction {

    public static final String ID = makeID("Modify");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    boolean random, x2;
    ArrayList<AbstractCard> coldCards, notColdCards;
    CardGroup cardGroup;

    public IncreaseColdAction(boolean random, int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        cardGroup = null;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;

        coldCards = new ArrayList<>();
        notColdCards = new ArrayList<>();
        this.random = random;
        x2 = amount < 0;
        this.amount = amount;
    }

    public IncreaseColdAction(CardGroup group, int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        cardGroup = group;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;

        coldCards = new ArrayList<>();
        notColdCards = new ArrayList<>();
        random = false;
        x2 = amount < 0;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (cardGroup == null) {
                for (AbstractCard c : Wiz.adp().hand.group) {
                    if (CardTemperatureFields.getCardHeat(c) < 0)
                        coldCards.add(c);
                    else
                        notColdCards.add(c);
                }

                Wiz.adp().hand.group.removeAll(notColdCards);

                if (Wiz.adp().hand.size() == 0) {
                    isDone = true;
                    returnCards();
                    return;
                }

                if (Wiz.adp().hand.size() <= 1) {
                    isDone = true;
                    for (AbstractCard c : Wiz.adp().hand.group)
                        increaseMods(c);
                    returnCards();
                    return;
                }
                if (random) {
                    isDone = true;
                    AbstractCard card = Wiz.adp().hand.getRandomCard(true);
                    increaseMods(card);
                    returnCards();
                    return;
                }

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false, true);
                tickDuration();
                return;
            } else {
                isDone = true;
                for (AbstractCard c : cardGroup.group)
                    increaseMods(c);
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                increaseMods(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : notColdCards)
            Wiz.adp().hand.addToTop(c);

        Wiz.adp().hand.refreshHandLayout();
    }

    private void increaseMods(AbstractCard card) {
        CardTemperatureFields.addHeat(card, -amount);
        card.superFlash();
        card.applyPowers();
    }
}
