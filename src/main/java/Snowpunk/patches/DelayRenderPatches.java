package Snowpunk.patches;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.text.DecimalFormat;

public class DelayRenderPatches {
    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class DelayRenderField {
        public static SpireField<Boolean> delay = new SpireField<>(() -> false);
    }

    public static boolean isDelayed() {
        return DelayRenderField.delay.get(Wiz.adp());
    }

    public static void delayRender() {
        DelayRenderField.delay.set(Wiz.adp(), true);
    }

    public static void resumeRender() {
        DelayRenderField.delay.set(Wiz.adp(), false);
    }

    @SpirePatch2(clz = AbstractRoom.class, method = "render")
    public static class PlayerRenderDelay {
        @SpirePostfixPatch
        public static void doRender(SpriteBatch sb) {
            if (isDelayed()) {
                Wiz.adp().render(sb);
            }
        }

        @SpireInstrumentPatch
        public static ExprEditor plz() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("render")) {
                        m.replace("if(!Snowpunk.patches.DelayRenderPatches.isDelayed()) {$proceed($$);}");
                    }
                }
            };
        }
    }
}
