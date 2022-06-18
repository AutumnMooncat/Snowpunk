package Snowpunk.actions;

import Snowpunk.util.Wiz;
import Snowpunk.vfx.ExhumeEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Predicate;

public class ExhumeRandomCardToDrawPileAction extends AbstractGameAction {
    Predicate<AbstractCard> filter;

    public ExhumeRandomCardToDrawPileAction() {
        this(c -> true);
    }

    public ExhumeRandomCardToDrawPileAction(Predicate<AbstractCard> p) {
        this.filter = p;
    }

    @Override
    public void update() {
        CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : Wiz.adp().exhaustPile.group) {
            if (filter.test(c)) {
                validCards.addToTop(c);
            }
        }
        if (!validCards.isEmpty()) {
            AbstractCard card = validCards.getRandomCard(true);
            card.unhover();
            card.unfadeOut();
            card.lighten(true);
            card.fadingOut = false;
            AbstractDungeon.topLevelEffects.add(new ExhumeEffect(card));
        }
        this.isDone = true;
    }
}
