package Snowpunk.actions;

import Snowpunk.cards.Masterpiece;
import Snowpunk.cards.Prototype;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.powers.interfaces.OnClankPower;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

import static Snowpunk.cards.abstracts.ClankCard.tryClank;

public class CheckPrototypeAction extends AbstractGameAction {

    public static final Color CLANK_TINT = new Color(1, 209 / 255f, 209 / 255f, 1);
    Prototype card;

    public CheckPrototypeAction(Prototype card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (card.getGears() >= card.magicNumber) {
            addToTop(new ResetExhaustAction(card, true));
            addToTop(new MakeTempCardInHandAction(new Masterpiece()));
            clank(card.current_x, card.current_y);
            card.superFlash(CLANK_TINT.cpy());
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
            addToTop(new SFXAction("snowpunk:masterpiece"));
        }
        isDone = true;
    }

    private void clank(float x, float y) {
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));
        if (!Settings.DISABLE_EFFECTS) {
            for (int i = 0; i < 30; ++i)
                AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        }
    }
}
