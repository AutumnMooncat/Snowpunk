package Snowpunk.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class UpgradeWithVisualAction extends AbstractGameAction {
    private AbstractPlayer p;

    AbstractCard card;

    public UpgradeWithVisualAction(AbstractCard card) {
        this.card = card;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        card.upgrade();
        card.superFlash();
        clank(card.current_x, card.current_y);
        card.applyPowers();
        isDone = true;
    }


    private void clank(float x, float y) {
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));
        addToTop(new VFXAction(new ImpactSparkEffect(x, y)));
        addToTop(new VFXAction(new ImpactSparkEffect(x, y)));
        //addToTop(new SFXAction("snowpunk:clank"));
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 30; ++i)
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        }
    }
}
