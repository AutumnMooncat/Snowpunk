package Snowpunk.vfx;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

public class CondenseEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5F;
    private AbstractCard card;
    boolean addToHand;

    public CondenseEffect(AbstractCard card, boolean addToHand) {
        this.card = card;
        this.duration = EFFECT_DUR;
        this.card.target_x = this.card.current_x = 198.0F * Settings.scale;
        this.card.target_y = this.card.current_y = 334.0F * Settings.scale;

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;

        CardCrawlGame.sound.play("CARD_OBTAIN");
        EvaporatePanel.evaporatePile.removeCard(this.card);
        this.addToHand = addToHand;
        if (addToHand) {
            if (Wiz.adp().hand.size() < BaseMod.MAX_HAND_SIZE)
                Wiz.adp().hand.addToTop(this.card);
            else {
                Wiz.adp().discardPile.addToTop(this.card);
                AbstractDungeon.player.createHandIsFullDialog();
            }
        } else
            Wiz.adp().drawPile.addToRandomSpot(this.card);
    }

    public void update() {
        if (addToHand)
            duration = 0;
        duration -= Gdx.graphics.getDeltaTime();
        card.update();
        if (duration < 0.0F) {
            isDone = true;
            if (!addToHand) {
                card.shrink();
                AbstractDungeon.getCurrRoom().souls.onToDeck(card, true, true);
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (!isDone) {
            card.render(sb);
        }
    }

    @Override
    public void dispose() {}
}
