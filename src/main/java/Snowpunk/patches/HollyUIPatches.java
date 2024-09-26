package Snowpunk.patches;

import Snowpunk.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
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
    public static final String ID = makeID(HollyUIPatches.class.getSimpleName());
    public static Hitbox tipHitbox = new Hitbox(0.0F, 0.0F, 128 * Settings.scale, 128 * Settings.scale);// 35
    //public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(modID + ":HollyUI");
    public static final String[] TEXT = uiString.TEXT;
    public static boolean showHolly = false;

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
                spriteBatch.draw(texture, x - texture.getWidth() * Settings.scale / 2.0f, y - texture.getHeight() * Settings.scale / 2.0f, texture.getWidth() * Settings.scale, texture.getHeight() * Settings.scale);
                String display = String.valueOf(HollyPatches.Holly.amount);
                display += "/" + String.valueOf(HollyPatches.Holly.THRESHOLD);

                float tempScale = FontHelper.energyNumFontBlue.getScaleX();
                FontHelper.energyNumFontBlue.getData().setScale(tempScale * .75f);
                FontHelper.renderFontCentered(spriteBatch, FontHelper.energyNumFontBlue, display, x, y);
                FontHelper.energyNumFontBlue.getData().setScale(tempScale);

                if (tipHitbox.hovered)
                    TipHelper.renderGenericTip(x + 75.0F * Settings.scale, y, TEXT[0], TEXT[1]);
            }
        } else {
            showHolly = false;
            HollyPatches.Holly.amount = 0;
        }
    }
}
