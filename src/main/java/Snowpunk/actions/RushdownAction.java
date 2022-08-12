package Snowpunk.actions;

import Snowpunk.patches.DelayRenderPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.HashMap;
import java.util.Iterator;

public class RushdownAction extends AbstractGameAction {
    final float xB;
    final float speed = 50f * Settings.scale;
    final HashMap<AbstractMonster, Boolean> hitMap = new HashMap<>();
    final HashMap<AbstractMonster, Boolean> targetToTheRight = new HashMap<>();
    final boolean flipped;
    boolean firstPass = true;
    boolean firstPhase = true;
    boolean secondPhase = false;
    float dx;
    int[] damages;
    DamageInfo.DamageType damageType;
    int blockPerHit;

    public RushdownAction(AbstractCreature source, int[] damages, DamageInfo.DamageType damageType) {
        this(source, damages, damageType, -1);
    }

    public RushdownAction(AbstractCreature source, int[] damages, DamageInfo.DamageType damageType, int blockPerHit) {
        this.damages = damages;
        this.source = source;
        this.flipped = source.flipHorizontal;
        this.xB = source.drawX;
        this.damageType = damageType;
        this.blockPerHit = blockPerHit;
    }


    @Override
    public void update() {
        if (firstPass) {
            DelayRenderPatches.delayRender();
            firstPass = false;
            for (AbstractMonster aM : AbstractDungeon.getMonsters().monsters) {
                if (!aM.isDeadOrEscaped()) {
                    hitMap.put(aM, false);
                    targetToTheRight.put(aM, aM.hb.cX >= source.hb.cX);
                }
            }
            if (flipped) {
                source.flipHorizontal = false;
            }
        }
        if (firstPhase) {
            dx = speed;
            source.drawX += dx;
            source.hb.move(source.hb.cX+dx,source.hb.cY);
            for (AbstractMonster aM : hitMap.keySet()) {
                if (!hitMap.get(aM) && targetToTheRight.get(aM) && source.hb.cX >= aM.hb.cX) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(aM.hb.cX, aM.hb.cY, AttackEffect.BLUNT_HEAVY));
                    aM.damage(new DamageInfo(source, damages[AbstractDungeon.getMonsters().monsters.indexOf(aM)], damageType));
                    if (blockPerHit > 0) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.source.hb.cX, this.source.hb.cY, AttackEffect.SHIELD));
                        source.addBlock(blockPerHit);
                    }
                    hitMap.put(aM, true);
                }
            }
            if (source.drawX > Settings.WIDTH + (2 * speed)) {
                firstPhase = false;
                secondPhase = true;
                source.drawX -= (Settings.WIDTH + (4 * speed));
                source.hb.move(source.hb.cX-(Settings.WIDTH + (4 * speed)),source.hb.cY);
            }
        }
        if (secondPhase) {
            dx = limitMovement(speed, xB-source.drawX);
            source.drawX += dx;
            source.hb.move(source.hb.cX+dx,source.hb.cY);
            for (AbstractMonster aM : hitMap.keySet()) {
                if (!hitMap.get(aM) && !targetToTheRight.get(aM) && source.hb.cX >= aM.hb.cX) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(aM.hb.cX, aM.hb.cY, AttackEffect.BLUNT_HEAVY));
                    aM.damage(new DamageInfo(source, damages[AbstractDungeon.getMonsters().monsters.indexOf(aM)], damageType));
                    if (blockPerHit > 0) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.source.hb.cX, this.source.hb.cY, AttackEffect.SHIELD));
                        source.addBlock(blockPerHit);
                    }
                    hitMap.put(aM, true);
                }
            }
            if (source.drawX == xB) {
                secondPhase = false;
                isDone = true;
            }
        }
        if (isDone) {
            DelayRenderPatches.resumeRender();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                c.applyPowers();
            }
            if (flipped) {
                source.flipHorizontal = true;
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
    }

    public float limitMovement(float desiredSpeed, float maxSpeed) {
        return (Math.abs(desiredSpeed) > Math.abs(maxSpeed)) ? maxSpeed : desiredSpeed;
    }
}
