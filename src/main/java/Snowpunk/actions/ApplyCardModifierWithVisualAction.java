package Snowpunk.actions;

import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static Snowpunk.SnowpunkMod.makeID;

public class ApplyCardModifierWithVisualAction extends AbstractGameAction {
    private AbstractCardModifier mod;
    private AbstractCard card = null;

    public ApplyCardModifierWithVisualAction(AbstractCard card, AbstractCardModifier mod) {
        this.mod = mod;
        this.card = card;
    }

    @Override
    public void update() {
        CardModifierManager.addModifier(card, mod.makeCopy());
        card.superFlash();
        if (MathUtils.randomBoolean())
            clank(card.current_x, card.current_y);
        else
            wrench();
        card.applyPowers();
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

    private void wrench() {
        Wiz.att(new VFXAction(Wiz.adp(), new WrenchEffect(card), WrenchEffect.DURATION, false));
    }
}
