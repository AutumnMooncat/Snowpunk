package Snowpunk.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import java.util.ArrayList;

public class IgnoreEnemyPowersPatches {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class IgnoreField {
        public static SpireField<Boolean> ignore = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class Yeet {
        public static ArrayList<AbstractPower> empty = new ArrayList<>();
        @SpireInstrumentPatch
        public static ExprEditor plz() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(AbstractMonster.class.getName()) && f.getFieldName().equals("powers")) {
                        f.replace("$_ = Snowpunk.patches.IgnoreEnemyPowersPatches.Yeet.powerCheck(this, $proceed($$));");
                    }
                }
            };
        }

        public static ArrayList<AbstractPower> powerCheck(AbstractCard __instance, ArrayList<AbstractPower> powers) {
            if (IgnoreField.ignore.get(__instance)) {
                return empty;
            }
            return powers;
        }
    }
}
