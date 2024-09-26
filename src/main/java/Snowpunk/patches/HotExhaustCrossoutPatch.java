package Snowpunk.patches;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.ShrinkLongDescription;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;

import java.util.HashSet;

import static Snowpunk.SnowpunkMod.makeID;

public class HotExhaustCrossoutPatch {
    @SpirePatch(clz = AbstractCard.class, method = "renderDescription")
    public static class CrossOutExhaustIfHot {

        public static final String ID = makeID("Hot");
        public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;


        @SpireInsertPatch(rloc = 50, /*locator=Locator.class,*/ localvars = {"spacing", "i", "start_x", "draw_y", "font", "textColor", "tmp", "gl"})
        public static void Insert(AbstractCard __instance, SpriteBatch sb, float spacing, int i, @ByRef float[] start_x, float draw_y, BitmapFont font, Color textColor, @ByRef String[] tmp, GlyphLayout gl) {
            String key = tmp[0].trim();
            if (__instance.exhaust && key.length() >= TEXT[0].length() && key.contains(TEXT[0]) && CardTemperatureFields.getCardHeat(__instance) >= CardTemperatureFields.HOT) {
                Color original = sb.getColor();
                sb.setColor(Color.RED);
                sb.draw(strikethrough,
                        start_x[0],
                        draw_y + i * 1.45f * -font.getCapHeight() - 9f * __instance.drawScale * Settings.scale,
                        gl.width,
                        3f * __instance.drawScale * Settings.scale
                );
                sb.setColor(original);
            }
        }

        //From Minty Spire
        public static final Texture whitePixel;
        public static final TextureRegion strikethrough;

        static {
            Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGB888);
            pm.setColor(0xffffffff);
            pm.drawPixel(0, 0);
            whitePixel = new Texture(pm);
            whitePixel.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            strikethrough = new TextureRegion(whitePixel);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderDescriptionCN")
    public static class CrossOutExhaustIfHotSCV {

        public static final String ID = makeID("Hot");
        public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;


        @SpireInsertPatch(rloc = 85, /*locator=Locator.class,*/ localvars = {"spacing", "i", "start_x", "draw_y", "font", "textColor", "tmp", "gl"})
        public static void Insert(AbstractCard __instance, SpriteBatch sb, float spacing, int i, @ByRef float[] start_x, float draw_y, BitmapFont font, Color textColor, @ByRef String[] tmp, GlyphLayout gl) {
            String key = tmp[0].trim();
            if (__instance.exhaust && key.length() >= TEXT[0].length() && key.contains(TEXT[0]) && CardTemperatureFields.getCardHeat(__instance) >= CardTemperatureFields.HOT) {
                Color original = sb.getColor();
                sb.setColor(Color.RED);
                sb.draw(strikethrough,
                        start_x[0],
                        draw_y + i * 1.45f * -font.getCapHeight() - 9f * __instance.drawScale * Settings.scale,
                        gl.width,
                        3f * __instance.drawScale * Settings.scale
                );
                sb.setColor(original);
            }
        }

        //From Minty Spire
        public static final Texture whitePixel;
        public static final TextureRegion strikethrough;

        static {
            Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGB888);
            pm.setColor(0xffffffff);
            pm.drawPixel(0, 0);
            whitePixel = new Texture(pm);
            whitePixel.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            strikethrough = new TextureRegion(whitePixel);
        }
    }
}
