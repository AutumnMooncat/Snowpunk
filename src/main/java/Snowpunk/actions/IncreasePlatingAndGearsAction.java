package Snowpunk.actions;

import Snowpunk.cardmods.*;
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

public class IncreasePlatingAndGearsAction extends AbstractGameAction {

    public static final String ID = makeID("Modify");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    boolean random, x2;
    ArrayList<AbstractCard> modCards, nonModCards;
    CardGroup cardGroup;
    int gearAmount;

    public IncreasePlatingAndGearsAction(boolean random, int amount, int gearAmount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        cardGroup = null;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;

        modCards = new ArrayList<>();
        nonModCards = new ArrayList<>();
        this.random = random;
        x2 = amount < 0;
        this.amount = amount;
        this.gearAmount = gearAmount;
    }

    public IncreasePlatingAndGearsAction(CardGroup group, int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        cardGroup = group;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;

        modCards = new ArrayList<>();
        nonModCards = new ArrayList<>();
        random = false;
        x2 = amount < 0;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (cardGroup == null) {
                for (AbstractCard c : Wiz.adp().hand.group) {
                    if (CardModifierManager.hasModifier(c, GearMod.ID) || CardModifierManager.hasModifier(c, PlateMod.ID))
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
        for (AbstractCard c : nonModCards)
            Wiz.adp().hand.addToTop(c);

        Wiz.adp().hand.refreshHandLayout();
    }

    private void increaseMods(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, GearMod.ID))
            CardModifierManager.addModifier(card, new GearMod(gearAmount));
        if (CardModifierManager.hasModifier(card, PlateMod.ID))
            CardModifierManager.addModifier(card, new PlateMod(x2 ? ((PlateMod) CardModifierManager.getModifiers(card, PlateMod.ID).get(0)).amount : amount));
        card.superFlash();
        card.applyPowers();
    }
}
