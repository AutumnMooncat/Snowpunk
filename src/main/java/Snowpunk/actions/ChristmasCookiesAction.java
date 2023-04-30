package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ChristmasCookiesAction extends AbstractGameAction {
    private int healAmt;
    private DamageInfo info;

    public ChristmasCookiesAction(AbstractCreature target, DamageInfo info, int healAmt) {
        this.info = info;
        this.setValues(target, info);
        this.healAmt = healAmt;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FASTER && target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.BLUNT_HEAVY));
            target.damage(info);
            if (target.isDying || target.currentHealth <= 0)
                addToBot(new HealAction(Wiz.adp(), Wiz.adp(), healAmt));

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }

        tickDuration();
    }
}