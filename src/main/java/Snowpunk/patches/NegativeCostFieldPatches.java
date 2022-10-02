package Snowpunk.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class NegativeCostFieldPatches {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class NegativeCostField {
        public static SpireField<Boolean> isNegativeCost = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = AbstractCard.class, method = "getCost")
    public static class GetCost {
        @SpirePostfixPatch
        public static String renderANegative(String __result, AbstractCard __instance) {
            if (NegativeCostField.isNegativeCost.get(__instance)) {
                return "-1";
            }
            return __result;
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost")
    public static class PortraitViewCost {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") && m.getMethodName().equals("renderFont")) {
                        m.replace("if (((Boolean) Snowpunk.patches.NegativeCostFieldPatches.NegativeCostField.isNegativeCost.get(card)).booleanValue()) {" +
                                "$3 = \"-1\";" +
                                //"$4 = 674.0f * com.megacrit.cardcrawl.core.Settings.scale;" +
                                "}" +
                                "$_ = $proceed($$);");
                    }
                }
            };
        }
    }
}
