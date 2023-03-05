package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.OnCondensePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.CondenseEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Predicate;

public class CondenseAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;

    AbstractCard card = null;
    boolean addToHand = false;

    public CondenseAction() {
        this(c -> true);
        amount = 1;
        card = null;
    }

    public CondenseAction(boolean addToHand) {
        this(c -> true);
        amount = 1;
        card = null;
        this.addToHand = addToHand;
    }

    public CondenseAction(Predicate<AbstractCard> p) {
        this.filter = p;
        amount = 1;
        card = null;
    }

    public CondenseAction(int amount) {
        this(c -> true);
        this.amount = amount;
        card = null;
    }

    public CondenseAction(AbstractCard card) {
        this(c -> true);
        this.amount = 1;
        this.card = card;
    }

    @Override
    public void update() {
        if (card == null) // Condense random Evaporated Cards
        {
            CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
                if (filter.test(c)) {
                    validCards.addToTop(c);
                }
            }
            amount = Math.min(validCards.size(), amount);
            for (int i = 0; i < amount; i++) {
                if (!validCards.isEmpty()) {
                    AbstractCard randomCard = validCards.getRandomCard(true);
                    CondenseCard(randomCard);
                    validCards.removeCard(randomCard);
                }
            }
        } else
            CondenseCard(card); // Condense specific card

        isDone = true;
    }

    private void CondenseCard(AbstractCard cardToCondense) {
        cardToCondense.unhover();
        cardToCondense.unfadeOut();
        cardToCondense.lighten(true);
        cardToCondense.fadingOut = false;
        if (CardTemperatureFields.canModTemp(cardToCondense, -1))
            CardTemperatureFields.addHeat(cardToCondense, -1);
        AbstractDungeon.topLevelEffects.add(new CondenseEffect(cardToCondense, addToHand));
        triggerOnCondense();
    }


    private void triggerOnCondense() {
        if (Wiz.adp() != null) {
            for (AbstractPower power : Wiz.adp().powers) {
                if (power instanceof OnCondensePower)
                    ((OnCondensePower) power).onCondense();
            }
        }
    }
}
