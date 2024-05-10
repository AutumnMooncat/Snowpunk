package Snowpunk.actions;

import Snowpunk.cardmods.ClankCardMod;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.cards.abstracts.NonClankCard;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.powers.WrenchPower;
import Snowpunk.powers.interfaces.OnClankPower;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class ClankAction extends AbstractGameAction {

    public static final Color CLANK_TINT = new Color(1, 209 / 255f, 209 / 255f, 1);
    AbstractCard card;

    AbstractMonster monster;

    public ClankAction(AbstractCard card, AbstractMonster monster) {
        this.card = card;
        this.monster = monster;
        source = Wiz.adp();
    }

    public ClankAction(AbstractCard card) {
        this.card = card;
        monster = null;
        source = Wiz.adp();
    }

    @Override
    public void update() {
        if (checkIfClank())
            runClanks();
        else if (card instanceof NonClankCard)
            runNonClanks();
        isDone = true;
    }

    private boolean checkIfClank() {
        boolean tryClank = false;
        if (card instanceof ClankCard || checkCardModsForClank(card)) {
            tryClank = true;
        }
        if (tryClank)
            tryClank = tryClank(card);
        else if (card instanceof NonClankCard)
            runNonClanks();

        return tryClank;
    }

    private void runClanks() {
        if (card instanceof ClankCard)
            ((ClankCard) card).onClank(monster);
        for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
            if (mod instanceof ClankCardMod)
                ((ClankCardMod) mod).onClank(card);
        }
        addToTop(new VFXAction(new ImpactSparkEffect(card.current_x, card.current_y)));
        addToTop(new VFXAction(new ImpactSparkEffect(card.current_x, card.current_y)));
        clank(card.current_x, card.current_y);
        card.superFlash(CLANK_TINT.cpy());
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
        addToTop(new SFXAction("snowpunk:clank"));
        for (AbstractPower pow : Wiz.adp().powers) {
            if (pow instanceof OnClankPower)
                ((OnClankPower) pow).onClank(card);
        }
    }

    private void runNonClanks() {
        if (monster != null && card instanceof NonClankCard)
            ((NonClankCard) card).onNonClank((AbstractMonster) monster);
    }

    private boolean checkCardModsForClank(AbstractCard card) {
        for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
            if (mod instanceof ClankCardMod) {
                return true;
            }
        }
        return false;
    }

    private void clank(float x, float y) {
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));// 46
        if (!Settings.DISABLE_EFFECTS) {// 48
            for (int i = 0; i < 30; ++i) {// 52
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));// 53 55 56
            }

        }
    }

    private boolean tryClank(AbstractCard card) {
        if (Wiz.adp() != null && Wiz.adp().hasPower(WrenchPower.POWER_ID) && (Wiz.adp().getPower(WrenchPower.POWER_ID).amount > 0)) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            ((WrenchPower) Wiz.adp().getPower(WrenchPower.POWER_ID)).onClank(card);
            Wiz.adp().getPower(WrenchPower.POWER_ID).amount--;
            if (Wiz.adp().getPower(WrenchPower.POWER_ID).amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), Wiz.adp().getPower(WrenchPower.POWER_ID)));
            return false;
        }
        if (Wiz.adp() != null && Wiz.adp().hasPower(PermWrenchPower.POWER_ID) && (Wiz.adp().getPower(PermWrenchPower.POWER_ID).amount > 0)) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            Wiz.adp().getPower(PermWrenchPower.POWER_ID).amount--;
            if (Wiz.adp().getPower(PermWrenchPower.POWER_ID).amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), Wiz.adp().getPower(PermWrenchPower.POWER_ID)));
            return false;
        }
        return true;
    }
}
