package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ScatterDamageAction extends AbstractGameAction {
    private final AbstractCard card;
    private Consumer<HashMap<AbstractMonster, Integer>> callback;

    public ScatterDamageAction(AbstractCard card, AttackEffect effect) {
        this(card, effect, map -> {});
    }

    public ScatterDamageAction(AbstractCard card, AttackEffect effect, Consumer<HashMap<AbstractMonster, Integer> > callback) {
        this.card = card;
        this.attackEffect = effect;
        this.callback = callback;
        this.source = Wiz.adp();
    }

    public void update() {
        int backup = card.baseDamage;
        HashMap<AbstractMonster, Integer> damageMap = new HashMap<>();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                damageMap.put(m, 0);
            }
        }
        for (int i = 0 ; i < card.damage ; i++) {
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            damageMap.put(m, damageMap.get(m) + 1);
        }
        for (AbstractMonster m : damageMap.keySet()) {
            if (damageMap.get(m) > 0) {
                DamageInfo info = new DamageInfo(Wiz.adp(), damageMap.get(m), card.damageTypeForTurn);
                applyEnemyPowersOnly(info, m);
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, attackEffect));
                m.damage(info);
            }
        }
        callback.accept(damageMap);
        card.baseDamage = backup;
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }

    public void applyEnemyPowersOnly(DamageInfo info, AbstractCreature target) {
        info.output = info.base;
        info.isModified = false;
        float tmp = (float)info.output;
        for (AbstractPower p : target.powers) {
            tmp = p.atDamageReceive(tmp, info.type);
            if (info.base != tmp) {
                info.isModified = true;
            }
        }
        for (AbstractPower p : target.powers) {
            tmp = p.atDamageFinalReceive(tmp, info.type);
            if (info.base != tmp) {
                info.isModified = true;
            }
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        info.output = MathUtils.floor(tmp);
    }
}