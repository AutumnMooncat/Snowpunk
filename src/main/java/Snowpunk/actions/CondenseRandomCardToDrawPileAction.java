package Snowpunk.actions;

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

public class CondenseRandomCardToDrawPileAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;

    public CondenseRandomCardToDrawPileAction() {
        this(c -> true);
        amount = 1;
    }

    public CondenseRandomCardToDrawPileAction(Predicate<AbstractCard> p) {
        this.filter = p;
        amount = 1;
    }

    public CondenseRandomCardToDrawPileAction(int amount) {
        this(c -> true);
        this.amount = amount;
    }

    @Override
    public void update() {
        CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
            if (filter.test(c)) {
                validCards.addToTop(c);
            }
        }
        amount = Math.min(validCards.size(), amount);
        for (int i = 0; i < amount; i++) {
            if (!validCards.isEmpty()) {
                AbstractCard card = validCards.getRandomCard(true);
                card.unhover();
                card.unfadeOut();
                card.lighten(true);
                card.fadingOut = false;
                AbstractDungeon.topLevelEffects.add(new CondenseEffect(card));
                validCards.removeCard(card);
                triggerOnCondense();
            }
        }
        this.isDone = true;
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
