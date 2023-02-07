package Snowpunk.actions;

import Snowpunk.cardmods.ClockworkMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ClockworkTickAction extends AbstractGameAction {

    public static int tick = 0;
    ClockworkMod clockworkMod;
    AbstractCard card;

    public ClockworkTickAction(ClockworkMod clockwork, AbstractCard card) {
        clockworkMod = clockwork;
        this.card = card;
    }

    @Override
    public void update() {
        if (clockworkMod.amount < 0)
            clockworkMod.amount = 0;
        clockworkMod.amount++;

        addToTop(new WaitAction(.1f));
        addToTop(new WaitAction(.1f));

        if (tick % 2 == 0)
            addToTop(new SFXAction("snowpunk:tick"));
        else
            addToTop(new SFXAction("snowpunk:tock"));
        tick++;

        card.superFlash(Color.WHITE.cpy());
        isDone = true;
    }
}
