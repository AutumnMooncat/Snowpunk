package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DelayedHookAction extends AbstractGameAction {

    public DelayedHookAction(AbstractMonster monster) {
        target = monster;
    }

    @Override
    public void update() {
        Wiz.atb(new CandyCaneKillAction((AbstractMonster) target, Wiz.adp().currentBlock + Wiz.adp().currentHealth, false));
        isDone = true;
    }
}