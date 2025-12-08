package Snowpunk.actions;

import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.SleighRide;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class SleighRideAction extends AbstractGameAction {
    int amount;

    public SleighRideAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for (AbstractCard card : Wiz.adp().hand.group) {
            if (card instanceof SleighRide) {
                CardModifierManager.addModifier(card, new PlateMod(amount));
                clank(card.current_x, card.current_y);
            }
        }
        for (AbstractCard card : Wiz.adp().limbo.group) {
            if (card instanceof SleighRide)
                CardModifierManager.addModifier(card, new PlateMod(amount));
        }
        for (AbstractCard card : Wiz.adp().discardPile.group) {
            if (card instanceof SleighRide)
                CardModifierManager.addModifier(card, new PlateMod(amount));
        }
        for (AbstractCard card : Wiz.adp().exhaustPile.group) {
            if (card instanceof SleighRide)
                CardModifierManager.addModifier(card, new PlateMod(amount));
        }
        for (AbstractCard card : Wiz.adp().drawPile.group) {
            if (card instanceof SleighRide)
                CardModifierManager.addModifier(card, new PlateMod(amount));
        }
        for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
            if (card instanceof SleighRide)
                CardModifierManager.addModifier(card, new PlateMod(amount));
        }
        isDone = true;
    }

    private void clank(float x, float y) {
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));
        addToTop(new VFXAction(new ImpactSparkEffect(x, y)));
        addToTop(new VFXAction(new ImpactSparkEffect(x, y)));
        addToTop(new SFXAction("snowpunk:clank"));
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 30; ++i)
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        }
    }
}
