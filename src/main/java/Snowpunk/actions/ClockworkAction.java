package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ReApplyPowersAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ClockworkAction extends AbstractGameAction {

    public ClockworkAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        /*
        if (AbstractDungeon.actionManager.actions.size() > 1) {
            addToBot(this);
            return;
        }*/
        for (AbstractCard card : Wiz.adp().hand.group) {
            addToBot(new ClockworkTickAction(amount, card));
        }
        for (AbstractCard card : Wiz.adp().hand.group) {
            addToBot(new ApplyPowersAction(card));
        }
        isDone = true;
    }

    public class ApplyPowersAction extends AbstractGameAction {
        AbstractCard card;

        public ApplyPowersAction(AbstractCard card) {
            this.card = card;
        }

        @Override
        public void update() {
            card.applyPowers();
            isDone = true;
        }
    }
}