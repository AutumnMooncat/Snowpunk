package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

public class CandyCaneKillAction extends AbstractGameAction {
    AbstractMonster monster = null;
    boolean killMinion;
    int hp;

    float speed = 0;

    public CandyCaneKillAction(AbstractMonster target, int hp, boolean killMinion) {
        monster = target;
        this.hp = hp;
        this.killMinion = killMinion;
    }

    @Override
    public void update() {
        if ((monster.currentHealth > hp || monster.currentHealth <= 0) && (!killMinion || !monster.hasPower(MinionPower.POWER_ID))) {
            isDone = true;
            return;
        }
        if (monster != null && !monster.isDead) {
            monster.drawX -= speed;
            speed += 1f;
        }
        if (monster.drawX < -1000) {
            monster.damage(new DamageInfo(Wiz.adp(), monster.currentHealth * 2, DamageInfo.DamageType.HP_LOSS));

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();

            Wiz.att(new SFXAction("snowpunk:screm"));
            isDone = true;
        }
    }
}
