package Snowpunk.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class LoopcastField {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class LoopField {
        public static SpireField<Boolean> islooping = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            //Copy non-inherent heat over to the new card
            LoopField.islooping.set(result, LoopField.islooping.get(self));
            return result;
        }
    }

}
