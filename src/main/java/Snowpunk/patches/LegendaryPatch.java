package Snowpunk.patches;

import Snowpunk.TheConductor;
import Snowpunk.util.TexLoader;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup.RenderCardDescriptorsSCV;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

public class LegendaryPatch {
    private static Texture LEGENDARY_BANNER_512;
    private static Texture LEGENDARY_ATK_512;
    private static Texture LEGENDARY_SKILL_512;
    private static Texture LEGENDARY_POWER_512;
    private static Texture LEGENDARY_LEFT_512;
    private static Texture LEGENDARY_CENTER_512;
    private static Texture LEGENDARY_RIGHT_512;
    private static Texture LEGENDARY_BANNER_1024;
    private static Texture LEGENDARY_ATK_1024;
    private static Texture LEGENDARY_SKILL_1024;
    private static Texture LEGENDARY_POWER_1024;
    private static Texture LEGENDARY_LEFT_1024;
    private static Texture LEGENDARY_CENTER_1024;
    private static Texture LEGENDARY_RIGHT_1024;

    public static void initialize() {
        LEGENDARY_BANNER_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/banner_unique.png");
        LEGENDARY_ATK_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/frame_attack_unique.png");
        LEGENDARY_SKILL_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/frame_skill_unique.png");
        LEGENDARY_POWER_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/frame_power_unique.png");
        LEGENDARY_LEFT_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/unique_left.png");
        LEGENDARY_CENTER_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/unique_center.png");
        LEGENDARY_RIGHT_512 = TexLoader.getTexture("SnowpunkResources/images/rarity/512/unique_right.png");
        LEGENDARY_BANNER_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/banner_unique.png");
        LEGENDARY_ATK_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/frame_attack_unique.png");
        LEGENDARY_SKILL_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/frame_skill_unique.png");
        LEGENDARY_POWER_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/frame_power_unique.png");
        LEGENDARY_LEFT_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/unique_left.png");
        LEGENDARY_CENTER_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/unique_center.png");
        LEGENDARY_RIGHT_1024 = TexLoader.getTexture("SnowpunkResources/images/rarity/1024/unique_right.png");
    }

    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderBannerImage"
    )
    public static class BannerPatch {
        @SpirePrefixPatch
        public static SpireReturn bannerPrefix512(AbstractCard __instance, @ByRef SpriteBatch[] sb,
                                                  float drawX, float drawY) {
            if (__instance.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_ATK_512 == null)
                initialize();
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper",
                    SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
            Color color = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor");
            Texture texture = LegendaryPatch.LEGENDARY_BANNER_512;
            TextureAtlas.AtlasRegion img = texToAtReg(texture);
            img.offsetX = 94f;
            img.offsetY = 378f;
            img.originalHeight = 512;
            img.originalWidth = 512;
            method.invoke(__instance, sb[0], color, img, drawX, drawY);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderAttackPortrait"
    )
    public static class AttackFramePatch {
        @SpirePrefixPatch
        public static SpireReturn attackFramePrefix512(AbstractCard __instance, @ByRef SpriteBatch[] sb, float x, float y) {
            if (__instance.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper",
                    SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
            Color color = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor");
            Texture texture = LegendaryPatch.LEGENDARY_ATK_512;
            TextureAtlas.AtlasRegion img = texToAtReg(texture);
            img.offsetX = 124f;
            img.offsetY = 219f;
            img.originalHeight = 512;
            img.originalWidth = 512;
            method.invoke(__instance, sb[0], color, img, x, y);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderPowerPortrait"
    )
    public static class PowerFramePatch {
        @SpirePrefixPatch
        public static SpireReturn powerFramePrefix512(AbstractCard __instance, @ByRef SpriteBatch[] sb, float x, float y) {
            if (__instance.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper",
                    SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
            Color color = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor");
            Texture texture = LegendaryPatch.LEGENDARY_POWER_512;
            TextureAtlas.AtlasRegion img = texToAtReg(texture);
            img.offsetX = 121f;
            img.offsetY = 222f;
            img.originalHeight = 512;
            img.originalWidth = 512;
            method.invoke(__instance, sb[0], color, img, x, y);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderSkillPortrait"
    )
    public static class SkillFramePatch {
        @SpirePrefixPatch
        public static SpireReturn skillFramePrefix512(AbstractCard __instance, @ByRef SpriteBatch[] sb, float x, float y) {
            if (__instance.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper",
                    SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
            Color color = ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderColor");
            Texture texture = LegendaryPatch.LEGENDARY_SKILL_512;
            TextureAtlas.AtlasRegion img = texToAtReg(texture);
            img.offsetX = 123f;
            img.offsetY = 222f;
            img.originalHeight = 512;
            img.originalWidth = 512;
            method.invoke(__instance, sb[0], color, img, x, y);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = AbstractCard.class,
            method = "renderDynamicFrame"
    )
    public static class DynamicFramePatch {
        @SpirePrefixPatch
        public static SpireReturn dynamicFramePrefix512(AbstractCard __instance, @ByRef SpriteBatch[] sb, float x, float y,
                                                        float typeOffset, float typeWidth) {
            if (typeWidth <= 1.1f)
                return SpireReturn.Continue();
            if (__instance.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(AbstractCard.class,
                    "dynamicFrameRenderHelper",
                    SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class, float.class, float.class);
            Texture textureL = LegendaryPatch.LEGENDARY_LEFT_512;
            Texture textureC = LegendaryPatch.LEGENDARY_CENTER_512;
            Texture textureR = LegendaryPatch.LEGENDARY_RIGHT_512;
            TextureAtlas.AtlasRegion imgL = texToAtReg(textureL);
            TextureAtlas.AtlasRegion imgC = texToAtReg(textureC);
            TextureAtlas.AtlasRegion imgR = texToAtReg(textureR);
            imgL.originalHeight = 512;
            imgL.originalWidth = 512;
            imgC.originalHeight = 512;
            imgC.originalWidth = 512;
            imgR.originalHeight = 512;
            imgR.originalWidth = 512;
            imgL.offsetX = 228;
            imgL.offsetY = 224;
            imgC.offsetX = 241;
            imgC.offsetY = 224;
            imgR.offsetX = 272;
            imgR.offsetY = 224;
            method.invoke(__instance, sb[0], imgC, x, y, 0.0F, typeWidth);
            method.invoke(__instance, sb[0], imgL, x, y, -typeOffset, 1.0F);
            method.invoke(__instance, sb[0], imgR, x, y, typeOffset, 1.0F);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = SingleCardViewPopup.class,
            method = "renderCardBanner"
    )
    public static class LargeBannerPatch {
        @SpirePrefixPatch
        public static SpireReturn bannerPrefix1024(SingleCardViewPopup __instance, @ByRef SpriteBatch[] sb) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();
            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(SingleCardViewPopup.class,
                    "renderHelper", SpriteBatch.class, float.class, float.class, TextureAtlas.AtlasRegion.class);

            Texture texture = LegendaryPatch.LEGENDARY_BANNER_1024;
            TextureAtlas.AtlasRegion img = texToAtReg(texture);
            img.offsetX = 191f;
            img.offsetY = 741f;
            img.originalHeight = 1024;
            img.originalWidth = 1024;
            method.invoke(__instance, sb[0], Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, img);
            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = SingleCardViewPopup.class,
            method = "renderFrame"
    )
    public static class LargeFramePatch {
        @SpirePrefixPatch
        public static SpireReturn framePrefix1024(SingleCardViewPopup __instance, @ByRef SpriteBatch[] sb) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();
            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();

            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(SingleCardViewPopup.class,
                    "renderHelper", SpriteBatch.class, float.class, float.class, TextureAtlas.AtlasRegion.class);

            ReflectionHacks.RMethod method2 = ReflectionHacks.privateMethod(SingleCardViewPopup.class,
                    "renderDynamicFrame",
                    SpriteBatch.class, float.class, float.class, float.class, float.class);

            TextureAtlas.AtlasRegion img = null;
            float tOffset = 0.0f;
            float tWidth = 0.0f;
            if (card.type == AbstractCard.CardType.ATTACK) {
                tWidth = AbstractCard.typeWidthAttack;
                tOffset = AbstractCard.typeOffsetAttack;
                img = texToAtReg(LEGENDARY_ATK_1024);
            } else if (card.type == AbstractCard.CardType.POWER) {
                tWidth = AbstractCard.typeWidthPower;
                tOffset = AbstractCard.typeOffsetPower;
                img = texToAtReg(LEGENDARY_POWER_1024);
            } else {
                tWidth = AbstractCard.typeWidthSkill;
                tOffset = AbstractCard.typeOffsetSkill;
                img = texToAtReg(LEGENDARY_SKILL_1024);
            }
            img.originalHeight = 1024;
            img.originalWidth = 1024;

            if (card.type == AbstractCard.CardType.ATTACK) {
                img.offsetX = 253;
                img.offsetY = 442;
            } else if (card.type == AbstractCard.CardType.POWER) {
                img.offsetX = 246;
                img.offsetY = 448;
            } else {
                img.offsetX = 251;
                img.offsetY = 449;
            }

            method.invoke(__instance, sb[0], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, img);

            float[] tOffset2 = {tOffset};
            float[] tWidth2 = {tWidth};
            RenderCardDescriptorsSCV.Frame.Insert(__instance, sb[0], card, tOffset2, tWidth2);
            tOffset = tOffset2[0];
            tWidth = tWidth2[0];
            method2.invoke(__instance, sb[0], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, tOffset, tWidth);

            return SpireReturn.Return();
        }
    }

    @SpirePatch2(
            clz = SingleCardViewPopup.class,
            method = "renderDynamicFrame"
    )
    public static class LargeDynamicFramePatch {
        @SpirePrefixPatch
        public static SpireReturn dynamicFramePrefix1024(SingleCardViewPopup __instance, @ByRef SpriteBatch[] sb, float x, float y,
                                                         float typeOffset, float typeWidth) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card.rarity != TheConductor.Enums.LEGENDARY)
                return SpireReturn.Continue();

            if (LegendaryPatch.LEGENDARY_BANNER_512 == null)
                initialize();
            if (typeWidth <= 1.1f)
                return SpireReturn.Return();

            ReflectionHacks.RMethod method = ReflectionHacks.privateMethod(SingleCardViewPopup.class,
                    "dynamicFrameRenderHelper",
                    SpriteBatch.class, TextureAtlas.AtlasRegion.class, float.class, float.class);

            TextureAtlas.AtlasRegion imgC = texToAtReg(LEGENDARY_CENTER_1024);
            TextureAtlas.AtlasRegion imgL = texToAtReg(LEGENDARY_LEFT_1024);
            TextureAtlas.AtlasRegion imgR = texToAtReg(LEGENDARY_RIGHT_1024);

            imgC.originalWidth = 1024;
            imgL.originalWidth = 1024;
            imgR.originalWidth = 1024;
            imgC.originalHeight = 1024;
            imgL.originalHeight = 1024;
            imgR.originalHeight = 1024;
            imgC.offsetX = 483;
            imgC.offsetY = 449;
            imgL.offsetX = 454;
            imgL.offsetY = 449;
            imgR.offsetX = 545;
            imgR.offsetY = 449;

            method.invoke(__instance, sb[0], imgC, 0.0F, typeWidth);
            method.invoke(__instance, sb[0], imgL, -typeOffset, 1.0F);
            method.invoke(__instance, sb[0], imgR, typeOffset, 1.0F);
            return SpireReturn.Return();
        }
    }

    public static TextureAtlas.AtlasRegion texToAtReg(Texture texture) {
        return new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }
}
