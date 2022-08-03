package Snowpunk.patches;

import Snowpunk.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ObtainCardPatch {
    static boolean reloadingSave = false;
    @SpirePatch2(clz = CardGroup.class, method = "addToTop")
    @SpirePatch2(clz = CardGroup.class, method = "addToBottom")
    @SpirePatch2(clz = CardGroup.class, method = "addToRandomSpot")
    public static class MasterDeckAddition {
        @SpirePrefixPatch
        public static void hookTime(CardGroup __instance, AbstractCard c) {
            if (!reloadingSave && __instance.type == CardGroup.CardGroupType.MASTER_DECK && c instanceof OnObtainCard) {
                ((OnObtainCard) c).onObtain();
            }
        }
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = "loadPlayerSave")
    public static class DontTriggerOnReload {
        @SpirePrefixPatch
        public static void disable() {
            reloadingSave = true;
        }
        @SpirePostfixPatch
        public static void enable() {
            reloadingSave = false;
        }
    }
}
