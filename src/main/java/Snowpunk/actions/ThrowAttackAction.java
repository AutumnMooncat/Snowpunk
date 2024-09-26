package Snowpunk.actions;

import Snowpunk.vfx.HorizontalThrowEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ThrowAttackAction extends AbstractGameAction {
    private DamageInfo info;

    private Color color;
    AbstractCard card;
    private int numTimes;

    public ThrowAttackAction(AbstractCreature target, DamageInfo info, int numTimes, Color color) {
        this.info = info;// 17
        this.target = target;// 18
        this.actionType = ActionType.DAMAGE;// 19
        this.attackEffect = AttackEffect.NONE;// 28
        this.duration = 0.01F;// 21
        this.numTimes = numTimes;// 22
        this.color = color;
    }

    public ThrowAttackAction(DamageInfo info, int numTimes, Color color) {
        this.info = info;// 26
        this.actionType = ActionType.DAMAGE;// 27
        this.attackEffect = AttackEffect.NONE;// 28
        this.duration = 0.01F;// 29
        this.numTimes = numTimes;// 31
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 32
            this.addToTop(new ThrowAttackAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes, color));// 33 35
        }
        this.color = color;
    }

    public void update() {
        if (target == null)
            isDone = true;
        else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            isDone = true;
        } else {
            if (target.currentHealth > 0) {
                if (numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    numTimes--;
                    addToTop(new ThrowAttackAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes, color));
                }
                info.applyPowers(info.owner, target);
                addToTop(new DamageAction(target, info, AttackEffect.BLUNT_HEAVY, true));
//                if (target.hb != null)
//                    addToTop(new VFXAction(new HorizontalThrowEffect(target.hb.cX, target.hb.cY, color)));
            } else
                addToTop(new ThrowAttackAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info, numTimes, color));

            isDone = true;
        }
    }
}