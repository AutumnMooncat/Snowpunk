package Snowpunk.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class SCostFieldPatches {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class SCostField {
        public static SpireField<Boolean> isSCost = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = AbstractCard.class, method = "getCost")
    public static class GetCost {
        @SpirePostfixPatch
        public static String renderAnS(String __result, AbstractCard __instance) {
            if (SCostField.isSCost.get(__instance)) {
                return "S";
            }
            return __result;
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost")
    public static class PortraitViewCost {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException
                {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") && m.getMethodName().equals("renderFont")) {
                        m.replace("if (((Boolean) Snowpunk.patches.SCostFieldPatches.SCostField.isSCost.get(card)).booleanValue()) {" +
                                "$3 = \"S\";" +
                                //"$4 = 674.0f * com.megacrit.cardcrawl.core.Settings.scale;" +
                                "}" +
                                "$_ = $proceed($$);");
                    }
                }
            };
        }
    }
}
