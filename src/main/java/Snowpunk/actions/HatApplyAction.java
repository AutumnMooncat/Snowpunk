package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HatApplyAction extends AbstractGameAction {

    public HatApplyAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for (int i = 0; i < amount; i++)
            Wiz.atb(new ApplyCardModifierAction(Wiz.adp().hand.getRandomCard(true), new HatMod()));
        isDone = true;
    }
}