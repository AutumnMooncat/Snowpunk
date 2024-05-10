package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UpdateCardDescriptionAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ClockworkTickAction extends AbstractGameAction {

    public static int tick = 0;
    AbstractCard card;

    public ClockworkTickAction(int ticks, AbstractCard card) {
        this.card = card;
        amount = ticks;
    }

    @Override
    public void update() {

        addToTop(new WaitAction(.2f));
        addToTop(new ApplyCardModifierAction(card, new PlateMod(amount, true)));

        if (tick % 2 == 0)
            addToTop(new SFXAction("snowpunk:tick"));
        else
            addToTop(new SFXAction("snowpunk:tock"));
        tick++;

        card.superFlash(Color.WHITE.cpy());
        isDone = true;
    }
}
