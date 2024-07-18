package Snowpunk.actions;

import Snowpunk.cardmods.*;
import Snowpunk.cards.Combinatronics;
import Snowpunk.cards.FiveGoldenRings;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class CombinatronicsAction extends AbstractGameAction {

    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Combinatronics.ID);
    int step;
    ArrayList<AbstractCard> noDupeCards;
    AbstractCard card;

    public CombinatronicsAction() {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        step = 1;
        noDupeCards = new ArrayList<>();
        card = null;
    }

    public void update() {
        if (duration == startDuration && step == 1) {
            card = null;
            for (AbstractCard c : Wiz.adp().hand.group) {
                boolean match = false;
                for (AbstractCard c2 : Wiz.adp().hand.group) {
                    if (c != c2 && (c.costForTurn == c2.costForTurn || ((c.freeToPlay() || c.costForTurn == 0) && (c2.freeToPlay() || c2.costForTurn == 0)))) {
                        match = true;
                        break;
                    }
                }
                if (!match || c.cost == -2)
                    noDupeCards.add(c);
            }

            Wiz.adp().hand.group.removeAll(noDupeCards);

            if (Wiz.adp().hand.size() <= 1) {
                isDone = true;
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(cardStrings.EXTENDED_DESCRIPTION[0], 1, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && step == 1) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (card == null)
                    card = c.makeStatEquivalentCopy();
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            step = 2;
            duration = startDuration;
        }

        if (duration == startDuration && step == 2 && card != null) {
            for (AbstractCard c : Wiz.adp().hand.group) {
                if ((c.costForTurn == card.costForTurn || ((c.freeToPlay() || c.costForTurn == 0) && (card.freeToPlay() || card.costForTurn == 0))))
                    continue;
                noDupeCards.add(c);
            }

            Wiz.adp().hand.group.removeAll(noDupeCards);

            if (Wiz.adp().hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            }

            if (Wiz.adp().hand.size() == 1) {
                CardModifierManager.addModifier(Wiz.adp().hand.getTopCard(), new PlayCopyMod(card.makeStatEquivalentCopy()));
                isDone = true;
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(cardStrings.EXTENDED_DESCRIPTION[1], 1, false, false, false, false, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved && step == 2 && card != null) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                CardModifierManager.addModifier(c, new PlayCopyMod(card.makeStatEquivalentCopy()));
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
            isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : noDupeCards)
            Wiz.adp().hand.addToTop(c);
        /*if(card != null){
            Wiz.adp().hand.addToTop(card);
            Wiz.att(new ExhaustSpecificCardAction(card));
        }*/
        Wiz.adp().hand.refreshHandLayout();
    }
}
