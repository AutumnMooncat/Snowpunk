package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveFromOnePileToAnotherAction extends AbstractGameAction {
    AbstractCard card;
    CardGroup from, to;

    public MoveFromOnePileToAnotherAction(int number, CardGroup from, CardGroup to) {
        amount = number;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        card = null;
        this.from = from;
        this.to = to;
    }

    public MoveFromOnePileToAnotherAction(AbstractCard card, CardGroup from, CardGroup to) {
        amount = -1;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.from = from;
        this.to = to;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (card != null) {
                to.group.add(card);
                from.group.remove(card);
            } else if (amount >= from.group.size()) {
                to.group.addAll(from.group);
                from.clear();
            } else {
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                for (AbstractCard c : from.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
                }

                Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, amount, "", false, card -> true, cards -> {
                    for (AbstractCard c : cards) {
                        to.group.add(cardMap.get(c));
                        from.group.remove(cardMap.get(c));
                    }
                }));
            }
        }
        tickDuration();
    }
}
