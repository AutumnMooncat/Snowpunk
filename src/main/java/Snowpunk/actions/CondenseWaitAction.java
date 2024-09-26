package Snowpunk.actions;

import Snowpunk.vfx.CondenseEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CondenseWaitAction extends AbstractGameAction {
    @Override
    public void update() {
        for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
            if (effect instanceof CondenseEffect)
                return;
        }
        for (AbstractGameEffect effect : AbstractDungeon.effectList) {
            if (effect instanceof CondenseEffect)
                return;
        }
        isDone = true;
    }
}
