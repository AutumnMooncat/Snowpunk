package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.MinionPower;

import java.util.Iterator;

public class CandyCaneKillAction extends AbstractGameAction {
    AbstractMonster monster = null;
    boolean killMinion;
    int threshold;

    float speed = 0;

    public CandyCaneKillAction(AbstractMonster target, int threshold, boolean killMinion) {
        monster = target;
        this.threshold = threshold;
        this.killMinion = killMinion;
    }

    @Override
    public void update() {
        if (monster == null || (monster.currentHealth > threshold || monster.currentHealth <= 0) && (!killMinion || !monster.hasPower(MinionPower.POWER_ID))) {
            isDone = true;
            return;
        }
        if (!monster.isDead) {
            monster.drawX -= speed;
            speed += 1f;
        }

        if (monster.drawX < -1000) {
            boolean cantLose = AbstractDungeon.getCurrRoom().cannotLose;
            AbstractDungeon.getCurrRoom().cannotLose = false;
            monster.isDying = true;
            monster.die();

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !(monster instanceof AwakenedOne))
                AbstractDungeon.getCurrRoom().cannotLose = cantLose;

            if (monster instanceof Darkling) {
                monster.halfDead = true;
                DarklingsWhy();
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();

            Wiz.att(new SFXAction("snowpunk:screm"));
            isDone = true;
        }
    }

    private void DarklingsWhy() {
        boolean allDead = true;// 210
        Iterator var7 = AbstractDungeon.getMonsters().monsters.iterator();// 211

        AbstractMonster m;
        while (var7.hasNext()) {
            m = (AbstractMonster) var7.next();
            if (m.id.equals("Darkling") && !m.halfDead) {// 212
                allDead = false;// 213
            }
        }

        if (allDead) {
            AbstractDungeon.getCurrRoom().cannotLose = false;// 225
            var7 = AbstractDungeon.getMonsters().monsters.iterator();// 227

            while (var7.hasNext()) {
                m = (AbstractMonster) var7.next();
                m.die();// 228
            }
        }
    }
}
