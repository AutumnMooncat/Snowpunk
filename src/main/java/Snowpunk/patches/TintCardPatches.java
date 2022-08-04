package Snowpunk.patches;

import Snowpunk.cardmods.TemperatureMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;

public class TintCardPatches {
    //TODO make less ugly, add VFX
    @SpirePatch2(clz = AbstractCard.class, method = "renderImage")
    public static class TintCardBack {
        private static final Color backupColor = Color.WHITE.cpy();

        @SpirePrefixPatch
        public static void tintBefore(AbstractCard __instance, @ByRef Color[] ___renderColor) {
            Color tint = CardTemperatureFields.getCardTint(__instance).cpy();
            if (tint != null) {
                backupColor.set(___renderColor[0]);
                ___renderColor[0].set(tint);
            }
        }

        @SpirePostfixPatch
        public static void resetAfter(AbstractCard __instance, @ByRef Color[] ___renderColor) {
            Color tint = CardTemperatureFields.getCardTint(__instance);
            if (tint != null) {
                ___renderColor[0].set(backupColor);
            }
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
    public static class TintCardBackSCV {
        private static final Color backupColor = Color.WHITE.cpy();

        @SpireInsertPatch(locator = StartLocator.class)
        public static void tintBefore(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            Color tint = CardTemperatureFields.getCardTint(___card);
            if (tint != null) {
                backupColor.set(sb.getColor());
                sb.setColor(tint.cpy());
            }
        }

        @SpireInsertPatch(locator = EndLocator.class)
        public static void resetAfter(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            Color tint = CardTemperatureFields.getCardTint(___card);
            if (tint != null) {
                sb.setColor(backupColor);
            }
        }

        public static class StartLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderCardBack");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }

        public static class EndLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderCardTypeText");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
