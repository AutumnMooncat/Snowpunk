package Snowpunk.actions;

import Snowpunk.util.Wiz;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Snowpunk.util.Wiz.adp;

public class ResetExhaustAction extends AbstractGameAction {
    AbstractCard card;
    boolean exhaust;

    public ResetExhaustAction(AbstractCard card, boolean exhaust) {
        this.card = card;
        this.exhaust = exhaust;
    }

    @Override
    public void update() {
        card.exhaust = exhaust;
        for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
            if (a instanceof UseCardAction) {
                if (ReflectionHacks.getPrivate(a, UseCardAction.class, "targetCard") == card) {
                    ((UseCardAction) a).exhaustCard = this.exhaust;
                }
            }
        }
        isDone = true;
    }
}
