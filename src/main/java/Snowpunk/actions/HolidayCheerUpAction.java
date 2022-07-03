package Snowpunk.actions;

import Snowpunk.relics.ChristmasSpirit;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.util.Wiz.adp;

public class HolidayCheerUpAction extends AbstractGameAction {

    public HolidayCheerUpAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ChristmasSpirit christmasSpirit = checkChristmas();
        if (christmasSpirit != null)
            christmasSpirit.updateHolidayCheer(amount);
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
