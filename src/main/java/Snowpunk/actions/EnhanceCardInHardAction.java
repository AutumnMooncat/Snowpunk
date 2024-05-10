package Snowpunk.actions;

import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class EnhanceCardInHardAction extends AbstractGameAction {

    public static final String ID = makeID("Enhance");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    private final int timesToUpgrade;
    private List<AbstractCardModifier> modifiers = new ArrayList<>();
    private boolean random;

    public EnhanceCardInHardAction(int timesToUpgrade) {
        this(1, timesToUpgrade, null);
    }

    public EnhanceCardInHardAction(AbstractCardModifier cardModifier) {
        this(1, 0, new ArrayList<AbstractCardModifier>() {
            {
                add(cardModifier);
            }
        });
    }

    public EnhanceCardInHardAction(int numCards, int timesToUpgrade, List<AbstractCardModifier> modifiers) {
        this(numCards, timesToUpgrade, modifiers, false);
    }

    public EnhanceCardInHardAction(int numCards, int timesToUpgrade, List<AbstractCardModifier> modifiers, boolean random) {
        this.actionType = ActionType.CARD_MANIPULATION;
        amount = numCards;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.timesToUpgrade = timesToUpgrade;
        if (modifiers != null)
            this.modifiers.addAll(modifiers);
        this.random = random;
    }

    public void update() {
        if (duration == startDuration) {
            if (timesToUpgrade > 0 && modifiers == null) {
                for (AbstractCard card : Wiz.adp().hand.group) {
                    if (!card.canUpgrade())
                        cannotUpgrade.add(card);
                }
                Wiz.adp().hand.group.removeAll(cannotUpgrade);
            }

            if (Wiz.adp().hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            }

            if (Wiz.adp().hand.size() <= amount) {
                isDone = true;
                for (AbstractCard c : Wiz.adp().hand.group)
                    Enhance(c);
                returnCards();
                return;
            }

            if (random) {
                isDone = true;
                for (int i = 0; i < amount; i++) {
                    AbstractCard card = Wiz.adp().hand.getRandomCard(true);
                    Enhance(card);
                    cannotUpgrade.add(card);
                    Wiz.adp().hand.removeCard(card);
                }
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                Enhance(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard card : cannotUpgrade)
            Wiz.adp().hand.addToTop(card);

        Wiz.adp().hand.refreshHandLayout();
    }

    private void Enhance(AbstractCard card) {
        if (timesToUpgrade > 0) {
            for (int i = 0; i < timesToUpgrade && card.canUpgrade(); i++)
                card.upgrade();
        }
        if (modifiers != null) {
            for (AbstractCardModifier mod : modifiers)
                CardModifierManager.addModifier(card, mod.makeCopy());
        }
        card.superFlash();
        card.applyPowers();
    }
}
