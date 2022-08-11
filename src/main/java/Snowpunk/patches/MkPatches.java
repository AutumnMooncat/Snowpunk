package Snowpunk.patches;

import Snowpunk.cardmods.MkMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class MkPatches {
    public static final String ID = makeID(MkMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class MkField {
        public static SpireField<Integer> amount = new SpireField<>(() -> 0);
    }

    public static void addMk(AbstractCard card) {
        MkField.amount.set(card, MkField.amount.get(card)+1);
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onRenderTitle")
    public static class CardModsAreTooBuggy {
        @SpirePostfixPatch
        public static String plz(String __result, AbstractCard card) {
            if (MkField.amount.get(card) > 0) {
                __result += TEXT[0] + MkField.amount.get(card);
            }
            return __result;
        }
    }
}
