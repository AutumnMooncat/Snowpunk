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
        if (!adp().hasRelic(ChristmasSpirit.ID)) {
            adp().relics.add(new ChristmasSpirit(amount));
            adp().reorganizeRelics();
            ChristmasSpirit christmasSpirit = (ChristmasSpirit) adp().getRelic(ChristmasSpirit.ID);
            christmasSpirit.onEquip();
        } else {
            ChristmasSpirit christmasSpirit = (ChristmasSpirit) adp().getRelic(ChristmasSpirit.ID);
            if (christmasSpirit != null) {
                christmasSpirit.updateSpirit(amount);
                christmasSpirit.flash();
            }
        }
        isDone = true;
    }
}
