package Snowpunk.actions;

import Snowpunk.cardmods.ClankCardMod;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.cards.abstracts.NonClankCard;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.powers.ReverseNextClankPower;
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
    boolean skipNonClank = false;
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
        else if (card instanceof NonClankCard && !skipNonClank)
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
        else if (card instanceof NonClankCard && !skipNonClank)
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
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 30; ++i)
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        }
    }

    private boolean tryClank(AbstractCard card) {
        if (Wiz.adp() == null)
            return false;
        WrenchPower wrenchPower = (WrenchPower) Wiz.adp().getPower(WrenchPower.POWER_ID);
        PermWrenchPower permWrenchPower = (PermWrenchPower) Wiz.adp().getPower(PermWrenchPower.POWER_ID);
        ReverseNextClankPower reversePower = (ReverseNextClankPower) Wiz.adp().getPower(ReverseNextClankPower.POWER_ID);
        if (reversePower != null && reversePower.amount > 0) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            reversePower.amount--;
            reversePower.updateDescription();
            if (reversePower.amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), reversePower));
            if (card instanceof ClankCard)
                ((ClankCard) card).unClank(monster);
            skipNonClank = true;
            return false;
        }
        if (wrenchPower != null && wrenchPower.amount > 0) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            wrenchPower.onClank(card);
            wrenchPower.amount--;
            wrenchPower.updateDescription();
            if (wrenchPower.amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), wrenchPower));
            return false;
        }
        if (permWrenchPower != null && permWrenchPower.amount > 0) {
            Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
            permWrenchPower.amount--;
            permWrenchPower.updateDescription();
            if (permWrenchPower.amount == 0)
                Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(), permWrenchPower));
            return false;
        }
        return true;
    }
}
