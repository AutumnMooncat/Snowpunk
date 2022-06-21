package Snowpunk.patches;

import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.ModifyXCostPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AltCostPatch {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class AltCostField {
        public static SpireField<Boolean> usingAltCost = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = ModifyXCostPatch.class, method = "Insert")
    public static class CheckAltCost {
        static boolean wasFreeToPlay;

        @SpirePrefixPatch
        public static void reset(AbstractCard c) {
            AltCostField.usingAltCost.set(c, false);
            wasFreeToPlay = c.freeToPlayOnce;
        }

        @SpirePostfixPatch
        public static void check(AbstractCard c) {
            if (!wasFreeToPlay && c.freeToPlayOnce) {
                AltCostField.usingAltCost.set(c, true);
            }
        }
    }
}
