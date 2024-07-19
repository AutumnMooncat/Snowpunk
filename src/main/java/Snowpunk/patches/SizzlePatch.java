package Snowpunk.patches;

import Snowpunk.SnowpunkMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;

import static Snowpunk.SnowpunkMod.makeShaderPath;
import static Snowpunk.patches.CardTemperatureFields.getCardHeat;

public class SizzlePatch {
    public static ShaderProgram initSizzleShader(ShaderProgram sizzleShader) {
        if (sizzleShader == null) {
            try {
                sizzleShader = new ShaderProgram(
                        Gdx.files.internal(makeShaderPath("sizzle/vertex.vs")),
                        Gdx.files.internal(makeShaderPath("sizzle/fragment.fs"))
                );
                if (!sizzleShader.isCompiled()) {
                    System.err.println(sizzleShader.getLog());
                }
                if (!sizzleShader.getLog().isEmpty()) {
                    System.out.println(sizzleShader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("ERROR: Failed to init sizzle shader:");
                e.printStackTrace();
            }
        }
        return sizzleShader;
    }

    public static FrameBuffer createBuffer() {
        return createBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static FrameBuffer createBuffer(int sizeX, int sizeY) {
        return new FrameBuffer(Pixmap.Format.RGBA8888, sizeX, sizeY, false, false);
    }

    public static void beginBuffer(FrameBuffer fbo) {
        fbo.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
    }

    public static TextureRegion getBufferTexture(FrameBuffer fbo) {
        TextureRegion texture = new TextureRegion(fbo.getColorBufferTexture());
        texture.flip(false, true);
        return texture;
    }

    @SpirePatch(clz = AbstractCard.class, method = "render", paramtypez = SpriteBatch.class)
    public static class SmoothBySantanaFeatRobThomasOfMatchboxTwenty {
        public static ShaderProgram sizzleShader = null;
        private static final FrameBuffer fbo = createBuffer();

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCard __instance, SpriteBatch spriteBatch) {
            if(sizzleShader == null) {
                sizzleShader = initSizzleShader(sizzleShader);
            }
            if (!Settings.hideCards) {
                if (getCardHeat(__instance) > 0) {
                    TextureRegion t = cardToTextureRegion(__instance, spriteBatch);
                    spriteBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    ShaderProgram oldShader = spriteBatch.getShader();
                    spriteBatch.setShader(sizzleShader);
                    sizzleShader.setUniformf("u_time", SnowpunkMod.time);
//                    sizzleShader.setUniformf("u_dripAmount1", 0.75f);
//                    sizzleShader.setUniformf("u_dripAmount2", 0.5f);
//                    sizzleShader.setUniformf("u_dripSpeed", 0.2f);
//                    sizzleShader.setUniformf("u_dripStrength", 0.2f);

                    spriteBatch.draw(t, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
                    spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    spriteBatch.setShader(oldShader);
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }

        public static TextureRegion cardToTextureRegion(AbstractCard card, SpriteBatch sb) {
            sb.end();
            beginBuffer(fbo);
            sb.begin();
            IntBuffer buf_rgb = BufferUtils.newIntBuffer(16);
            IntBuffer buf_a = BufferUtils.newIntBuffer(16);
            Gdx.gl.glGetIntegerv(GL30.GL_BLEND_EQUATION_RGB, buf_rgb);
            Gdx.gl.glGetIntegerv(GL30.GL_BLEND_EQUATION_ALPHA, buf_a);

            Gdx.gl.glBlendEquationSeparate(buf_rgb.get(0), GL30.GL_MAX);
            Gdx.gl.glBlendEquationSeparate(GL30.GL_FUNC_ADD, GL30.GL_MAX);
            card.render(sb, false);
            Gdx.gl.glBlendEquationSeparate(GL30.GL_FUNC_ADD, GL30.GL_FUNC_ADD);
            Gdx.gl.glBlendEquationSeparate(buf_rgb.get(0), buf_a.get(0));

            sb.end();
            fbo.end();
            sb.begin();
            return getBufferTexture(fbo);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "render", paramtypez = SpriteBatch.class)
    public static class SmoothBySantanaFeatRobThomasOfMatchboxTwentySCV {
        public static ShaderProgram sizzleShader = null;
        private static ShaderProgram oldShader;

        @SpireInsertPatch(locator = SizzlePatch.SmoothBySantanaFeatRobThomasOfMatchboxTwentySCV.Locator.class)
        public static void ApplyShader(SingleCardViewPopup __instance, SpriteBatch sb) {
            if(sizzleShader == null) {
                sizzleShader = initSizzleShader(sizzleShader);
            }
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (getCardHeat(card) > 0) {
                oldShader = sb.getShader();
                sb.setShader(sizzleShader);
                sizzleShader.setUniformf("u_time", SnowpunkMod.time);
            }
        }

        @SpireInsertPatch(locator = SizzlePatch.SmoothBySantanaFeatRobThomasOfMatchboxTwentySCV.LocatorTwo.class)
        public static void RemoveShader(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (getCardHeat(card) > 0) {
                sb.setShader(oldShader);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderCardBack");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }

        private static class LocatorTwo extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderArrows");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }
}

