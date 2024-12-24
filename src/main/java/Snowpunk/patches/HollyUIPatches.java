package Snowpunk.patches;

import Snowpunk.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class HollyUIPatches {
    private static Texture texture = TexLoader.getTexture("SnowpunkResources/images/ui/BigHollyIcon.png");
    private static Texture glowTexture = TexLoader.getTexture("SnowpunkResources/images/ui/BigHollyGlowIcon.png");
    public static final String ID = makeID(HollyUIPatches.class.getSimpleName());
    public static Hitbox tipHitbox = new Hitbox(0.0F, 0.0F, 128 * Settings.scale, 128 * Settings.scale);// 35
    //public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(modID + ":HollyUI");
    public static final String[] TEXT = uiString.TEXT;
    public static boolean showHolly = false;
    public static float flash = 1f, timePassed = 0;

    @SpirePatch(clz = EnergyPanel.class, method = "render")
    public static class EnergyPanel_Render {
        @SpirePrefixPatch
        public static void prefix(EnergyPanel __instance, SpriteBatch sb) {

            tipHitbox.update();
            if (tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
                AbstractDungeon.overlayMenu.hoveredTip = true;
            }

            HollyUIPatches.Render(sb);
        }
    }

    public static void Render(SpriteBatch spriteBatch) {
        float x = Settings.WIDTH / 2f;
        float y = Settings.HEIGHT * .75f;
        tipHitbox.move(x, y);
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (HollyPatches.Holly.amount > 0 || showHolly) {
                showHolly = true;
                spriteBatch.setColor(1, 1, 1, 1);
                Texture tex = texture;
                if (HollyPatches.Holly.amount >= 12)
                    tex = glowTexture;
                spriteBatch.draw(tex, x - tex.getWidth() * Settings.scale * flash / 2.0f, y - tex.getHeight() * Settings.scale * flash / 2.0f, tex.getWidth() * Settings.scale * flash, tex.getHeight() * Settings.scale * flash);
                String display = String.valueOf(HollyPatches.Holly.amount);
                display += "/" + String.valueOf(HollyPatches.Holly.THRESHOLD);

                float tempScale = FontHelper.energyNumFontBlue.getScaleX();
                FontHelper.energyNumFontBlue.getData().setScale(tempScale * .75f);
                FontHelper.renderFontCentered(spriteBatch, FontHelper.energyNumFontBlue, display, x, y);
                FontHelper.energyNumFontBlue.getData().setScale(tempScale);

                if (tipHitbox.hovered)
                    TipHelper.renderGenericTip(x + 75.0F * Settings.scale, y, TEXT[0], TEXT[1]);
            }

            if (HollyPatches.Holly.amount >= 12)
                timePassed += 1f;
            if (timePassed > 120) {
                flash = 1.25f;
                timePassed -= 120;
            }
            if (flash > 1f) {
                flash *= .98f;
                if (flash < 1f)
                    flash = 1f;
            }
        } else {
            showHolly = false;
            HollyPatches.Holly.amount = 0;
        }
    }
}
