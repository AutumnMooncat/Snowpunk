package Snowpunk.actions;

import Snowpunk.relics.ChristmasSpirit;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static Snowpunk.util.Wiz.adp;

public class HolidayCheerUpAction extends AbstractGameAction {

    public HolidayCheerUpAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ChristmasSpirit christmasSpirit = checkChristmas();
        if (christmasSpirit != null)
            christmasSpirit.updateSpirit(amount);
        isDone = true;
    }

    private ChristmasSpirit checkChristmas() {
        if (!adp().hasRelic(ChristmasSpirit.ID)) {
            adp().relics.add(new ChristmasSpirit());
            adp().reorganizeRelics();
            ChristmasSpirit christmasSpirit = (ChristmasSpirit) adp().getRelic(ChristmasSpirit.ID);
            christmasSpirit.onEquip();
        }
        return (ChristmasSpirit) adp().getRelic(ChristmasSpirit.ID);
    }
}
