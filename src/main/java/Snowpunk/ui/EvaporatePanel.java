package Snowpunk.ui;

import Snowpunk.SnowpunkMod;
import Snowpunk.patches.EvaporatePanelPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.vfx.ExhaustPileParticle;

public class EvaporatePanel extends AbstractPanel {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SnowpunkMod.makeID("EvaporatePanel"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final float SHOW_X = 198.0F * Settings.scale;
    private static final float SHOW_Y = 300.0F * Settings.scale;
    private static final float HIDE_X = -480.0F * Settings.scale;
    private static final float HIDE_Y = 284.0F * Settings.scale;
    private static final float TIP_X = 50.0F * Settings.scale;
    private static final float TIP_Y = 450.0F * Settings.scale;
    private static final float COUNT_CIRCLE_W = 128.0F * Settings.scale;
    public static float fontScale;
    public static float energyVfxTimer;
    private final Hitbox hb;

    public static CardGroup evaporatePile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public EvaporatePanel() {
        super(SHOW_X, SHOW_Y, HIDE_X, HIDE_Y, null, false);
        this.hb = new Hitbox(0.0F, 0.0F, 100.0F * Settings.scale, 100.0F * Settings.scale);
    }

    @Override
    public void updatePositions() {
        super.updatePositions();
        if (!this.isHidden && evaporatePile.size() > 0) {
            this.hb.update();
            this.updateVfx();
        }

        if (this.hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD && AbstractDungeon.overlayMenu.combatPanelsShown)) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
            if (InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
            }
        }

        if ((this.hb.clicked) && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
            this.hb.clicked = false;
            this.hb.hovered = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
        } else {
            if ((this.hb.clicked) && AbstractDungeon.overlayMenu.combatPanelsShown && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.player.isDead && !evaporatePile.isEmpty()) {
                this.hb.clicked = false;
                this.hb.hovered = false;
                if (AbstractDungeon.isScreenUp) {
                    if (AbstractDungeon.previousScreen == null) {
                        AbstractDungeon.previousScreen = AbstractDungeon.screen;
                    }
                } else {
                    AbstractDungeon.previousScreen = null;
                }

                this.openExhaustPile();
            }

        }
    }

    private void openExhaustPile() {
        if (AbstractDungeon.player.hoveredCard != null) {
            AbstractDungeon.player.releaseCard();
        }

        AbstractDungeon.dynamicBanner.hide();
        EvaporatePanelPatches.RenderEvaporateInsteadField.renderEvaporate.set(AbstractDungeon.exhaustPileViewScreen, true);
        AbstractDungeon.exhaustPileViewScreen.open();
        this.hb.hovered = false;
        InputHelper.justClickedLeft = false;
    }

    private void updateVfx() {
        energyVfxTimer -= Gdx.graphics.getDeltaTime();
        if (energyVfxTimer <= 0.0F && !Settings.hideLowerElements) {
            AbstractDungeon.effectList.add(new ExhaustPileParticle(this.current_x, this.current_y));
            energyVfxTimer = 0.05F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!evaporatePile.isEmpty()) {
            this.hb.move(this.current_x, this.current_y);
            String msg = Integer.toString(evaporatePile.size());
            sb.setColor(Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            sb.draw(ImageMaster.DECK_COUNT_CIRCLE, this.current_x - COUNT_CIRCLE_W / 2.0F, this.current_y - COUNT_CIRCLE_W / 2.0F, COUNT_CIRCLE_W, COUNT_CIRCLE_W);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, this.current_x, this.current_y + 2.0F * Settings.scale, Settings.CREAM_COLOR);

            this.hb.render(sb);
            if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                TipHelper.renderGenericTip(TIP_X, TIP_Y, TEXT[0], TEXT[1]);
            }
        }
    }
}
