package Snowpunk.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class VentPatch {
    @SpirePatch2(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class VentTime {
        @SpirePrefixPatch
        public static SpireReturn<?> dontYeet(CardGroup __instance, AbstractCard c) {
            if (c.hasTag(CustomTags.VENT)) {
                __instance.moveToDiscardPile(c);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
