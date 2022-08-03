package Snowpunk.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class SmithEffect extends AbstractGameEffect {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean openedScreen = false;
    private Color screenColor;

    public SmithEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();// 32
        this.duration = 0.1F;// 38
        this.screenColor.a = 0.0F;// 39
        AbstractDungeon.overlayMenu.proceedButton.hide();// 40
    }// 41

    public void update() {
        if (!AbstractDungeon.isScreenUp) {// 45
            this.duration -= Gdx.graphics.getDeltaTime();// 46
            this.updateBlackScreenColor();// 47
        }

        if (openedScreen && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                c.upgrade();// 57
                AbstractDungeon.player.bottledCardUpgradeCheck(c);// 58
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));// 59
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        if (!this.openedScreen) {
            if (AbstractDungeon.player.masterDeck.getUpgradableCards().isEmpty()) {
                this.isDone = true;
                return;
            }
            this.openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, TEXT[0], true, false, false, false);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }// 92

    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {// 98
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);// 99
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);// 101
        }

    }// 103

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);// 107
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);// 108
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {// 110
            AbstractDungeon.gridSelectScreen.render(sb);// 111
        }

    }// 113

    public void dispose() {
    }// 118

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect");// 27
        TEXT = uiStrings.TEXT;// 28
    }
}
