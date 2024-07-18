package Snowpunk.ui;

import CardAugments.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.buttons.GotItButton;

import static Snowpunk.SnowpunkMod.makeID;

public class EvaporateTutorial extends FtueTip {
    private static final UIStrings uiText = CardCrawlGame.languagePack.getUIString(makeID("EvaporateTutorial"));

    private String header, body;
    private int x, y;
    private static final int W = 622, H = 284, drawW = 600, drawH = 240;
    private GotItButton button;

    public EvaporateTutorial() {
        super();
        openScreen(uiText.TEXT[0], uiText.TEXT[1], 350 * Settings.scale, Settings.HEIGHT * .43f);
        header = uiText.TEXT[0];
        body = uiText.TEXT[1];
    }

    @Override
    public void openScreen(String header, String body, float x, float y) {
        this.header = header;
        this.body = body;
        this.x = (int) x;
        this.y = (int) y;
        button = new GotItButton(x, y);

        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
    }

    @Override
    public void update() {
        button.update();
        if (button.hb.clicked || CInputActionSet.proceed.isJustPressed()) {
            CInputActionSet.proceed.unpress();
            CardCrawlGame.sound.play("DECK_OPEN");
            AbstractDungeon.closeCurrentScreen();
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(
                ImageMaster.FTUE,
                x - drawW / 2f,
                y - drawH / 2f,
                drawW / 2f,
                drawH / 2f,
                drawW,
                drawH,
                Settings.scale,
                Settings.scale,
                0f,
                0,
                0,
                W,
                H,
                false,
                false);

        sb.setColor(new Color(1f, 1f, 1f, 0.7f + (MathUtils.cosDeg(System.currentTimeMillis() / 2f % 360) + 1.25f) / 5f));
        button.render(sb);

        FontHelper.renderFontLeftTopAligned(
                sb,
                FontHelper.topPanelInfoFont,
                LABEL[0] + header,
                x - 190f * Settings.scale,
                y + 70f * Settings.scale,
                Settings.GOLD_COLOR);

        FontHelper.renderSmartText(
                sb,
                FontHelper.tipBodyFont,
                body,
                x - 250f * Settings.scale,
                y + 30f * Settings.scale,
                450f * Settings.scale,
                26f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderFontRightTopAligned(
                sb,
                FontHelper.topPanelInfoFont,
                LABEL[1],
                x + 194f * Settings.scale,
                y - 150f * Settings.scale,
                Settings.GOLD_COLOR);

        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(
                    CInputActionSet.proceed.getKeyImg(),
                    button.hb.cX - 32f + 130f * Settings.scale,
                    button.hb.cY - 32f + 2f * Settings.scale,
                    32f,
                    32f,
                    64,
                    64,
                    Settings.scale,
                    Settings.scale,
                    0f,
                    0,
                    0,
                    64,
                    64,
                    false,
                    false);
        }
    }
}

