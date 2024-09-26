package Snowpunk.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class UpgradeRandomInHardWithVisualAction extends AbstractGameAction {
    private AbstractPlayer p;

    public UpgradeRandomInHardWithVisualAction(int num) {
        amount = num;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.group.size() <= 0) {
                isDone = true;
                return;
            }
            CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.hand.group) {
                if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS)
                    upgradeable.addToTop(c);
            }
            if (upgradeable.size() > 0) {
                upgradeable.shuffle();
                for (int i = 0; i < amount && i < upgradeable.size(); i++) {
                    AbstractCard card = upgradeable.group.get(i);
                    card.upgrade();
                    card.superFlash();
                    clank(card.current_x, card.current_y);
                    card.applyPowers();
                }
            }
            isDone = true;
            return;
        }
        tickDuration();
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
