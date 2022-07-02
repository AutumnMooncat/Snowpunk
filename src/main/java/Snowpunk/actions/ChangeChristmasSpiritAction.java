package Snowpunk.actions;

import Snowpunk.relics.ChristmasSpirit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static Snowpunk.util.Wiz.adp;

public class ChangeChristmasSpiritAction extends AbstractGameAction {

    public ChangeChristmasSpiritAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ChristmasSpirit christmasSpirit = checkChristmas();
        if (christmasSpirit != null) {
            christmasSpirit.setCounter(christmasSpirit.counter + amount);
            christmasSpirit.flash();
        }
        isDone = true;
    }

    private ChristmasSpirit checkChristmas() {
        if (!adp().hasRelic(ChristmasSpirit.ID)) {
            adp().relics.add(new ChristmasSpirit());
            adp().reorganizeRelics();
        }
        return (ChristmasSpirit) adp().getRelic(ChristmasSpirit.ID);
    }
}
