package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
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
    int heat = 0;
    public EnhanceCardInHardAction(int timesToUpgrade) {
        this(1, timesToUpgrade, 0, null);
    }

    public EnhanceCardInHardAction(AbstractCardModifier cardModifier) {
        this(1, 0, 0, new ArrayList<AbstractCardModifier>() {
            {
                add(cardModifier);
            }
        });
    }

    public EnhanceCardInHardAction(int numCards, int timesToUpgrade, List<AbstractCardModifier> modifiers) {
        this(numCards, timesToUpgrade, 0, modifiers, false);
    }

    public EnhanceCardInHardAction(int numCards, int timesToUpgrade, List<AbstractCardModifier> modifiers, boolean random) {
        this(numCards, timesToUpgrade, 0, modifiers, random);
    }

    public EnhanceCardInHardAction(int numCards, int timesToUpgrade, int heat, List<AbstractCardModifier> modifiers) {
        this(numCards, timesToUpgrade, heat, modifiers, false);
    }

    public EnhanceCardInHardAction(int numCards, int timesToUpgrade, int heat, List<AbstractCardModifier> modifiers, boolean random) {
        this.actionType = ActionType.CARD_MANIPULATION;
        amount = numCards;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.timesToUpgrade = timesToUpgrade;
        if (modifiers != null)
            this.modifiers.addAll(modifiers);
        this.random = random;
        this.heat = heat;
    }

    public void update() {
        if (duration == startDuration) {
            if (timesToUpgrade > 0 && modifiers.size() == 0) {
                for (AbstractCard card : Wiz.adp().hand.group) {
                    if (!card.canUpgrade())
                        cannotUpgrade.add(card);
                    if (card.type == AbstractCard.CardType.STATUS)
                        cannotUpgrade.add(card);
                }
                Wiz.adp().hand.group.removeAll(cannotUpgrade);
            }

            if (Wiz.adp().hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            } else if (Wiz.adp().hand.size() <= amount) {
                isDone = true;
                for (AbstractCard c : Wiz.adp().hand.group)
                    Enhance(c);
                returnCards();
                return;
            } else if (random) {
                isDone = true;
                for (int i = 0; i < amount; i++) {
                    AbstractCard card = Wiz.adp().hand.getRandomCard(true);
                    Enhance(card);
                    if (amount > 1) {
                        cannotUpgrade.add(card);
                        Wiz.adp().hand.removeCard(card);
                    }
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
            if (!Wiz.adp().hand.contains(card))
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
        if (heat != 0)
            CardTemperatureFields.addHeat(card, heat);
        card.superFlash();
        card.applyPowers();
    }
}
