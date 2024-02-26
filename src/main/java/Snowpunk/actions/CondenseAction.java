package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.OnCondensePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.CondenseEffect;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

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
            CondenseCard(card);

        isDone = true;
    }

    private void CondenseCard(AbstractCard cardToCondense) {
        cardToCondense.unhover();
        cardToCondense.unfadeOut();
        cardToCondense.lighten(true);
        cardToCondense.fadingOut = false;
        AbstractDungeon.topLevelEffects.add(new CondenseEffect(cardToCondense, true)); //Always set to draw now

        AbstractGameEffect e = null;
        for (AbstractGameEffect effect : AbstractDungeon.effectList) {
            if (effect instanceof ExhaustCardEffect) {
                AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                if (c == cardToCondense)
                    e = effect;
            }
        }
        AbstractDungeon.effectList.remove(e);

        triggerOnCondense(cardToCondense);
    }


    private void triggerOnCondense(AbstractCard card) {
        if (Wiz.adp() != null) {
            for (AbstractPower power : Wiz.adp().powers) {
                if (power instanceof OnCondensePower)
                    ((OnCondensePower) power).onCondense(card);
            }
        }
    }
}
