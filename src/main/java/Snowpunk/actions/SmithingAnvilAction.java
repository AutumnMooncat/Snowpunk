package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class SmithingAnvilAction extends AbstractGameAction {
    private AbstractCard card;

    public SmithingAnvilAction(AbstractCard card) {
        this.card = card;
    }


    public void update() {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target != null) {
            card.calculateCardDamage((AbstractMonster) target);
            addToTop(new VFXAction(new ImpactSparkEffect(target.hb.cX, target.hb.cY)));
            addToTop(new VFXAction(new ImpactSparkEffect(target.hb.cX, target.hb.cY)));
            AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(target.hb.cX, target.hb.cY));
            addToTop(new SFXAction("snowpunk:clank"));
            addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), AttackEffect.NONE));
        }

        isDone = true;
    }// 47
}
