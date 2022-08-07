package Snowpunk.patches;

import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FreeCardPatch {
    @SpirePatch2(clz = AbstractCard.class, method = "freeToPlay")
    public static class FreeCardPlz {
        @SpirePrefixPatch
        public static SpireReturn<?> free(AbstractCard __instance) {
            if (Wiz.adp() != null && !AbstractDungeon.isScreenUp && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                for (AbstractPower pow : Wiz.adp().powers) {
                    if (pow instanceof FreeToPlayPower && ((FreeToPlayPower) pow).isFreeToPlay(__instance)) {
                        return SpireReturn.Return(true);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
