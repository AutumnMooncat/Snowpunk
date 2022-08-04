package Snowpunk.patches;

import Snowpunk.powers.interfaces.OnCreateCardPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class OnCreateCardPatches {
    @SpirePatch(clz = AbstractGameEffect.class, method = SpirePatch.CLASS)
    public static class ModifiedField {
        public static SpireField<Boolean> modified = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToHandEffect.class, method = "update")
    @SpirePatch(clz = ShowCardAndAddToDrawPileEffect.class, method = "update")
    public static class OnCreateCard {
        @SpirePrefixPatch
        public static void plz(AbstractGameEffect __instance, AbstractCard ___card) {
            if (!ModifiedField.modified.get(__instance)) {
                ModifiedField.modified.set(__instance, true);
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof OnCreateCardPower) {
                        ((OnCreateCardPower) p).onCreateCard(___card);
                    }
                }
            }
        }
    }
}
