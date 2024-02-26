package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.CondenseEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveExhaustedCardToEvaporatePileAction extends AbstractGameAction {

    public MoveExhaustedCardToEvaporatePileAction(int number) {
        amount = number;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (amount >= Wiz.adp().exhaustPile.group.size()) {
                EvaporatePanel.evaporatePile.group.addAll(Wiz.adp().exhaustPile.group);
                Wiz.adp().exhaustPile.clear();
            } else {
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                for (AbstractCard c : Wiz.adp().exhaustPile.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
                }

                Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, amount, "", false, card -> true, cards -> {
                    for (AbstractCard c : cards) {
                        EvaporatePanel.evaporatePile.group.add(cardMap.get(c));
                        Wiz.adp().exhaustPile.group.remove(cardMap.get(c));
                    }
                }));
            }
        }
        tickDuration();
    }
}
