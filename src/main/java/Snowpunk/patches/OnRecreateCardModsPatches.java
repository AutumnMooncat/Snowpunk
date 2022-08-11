package Snowpunk.patches;

import Snowpunk.cards.interfaces.OnRecreateCardModsCard;
import basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame.LoadPlayerSaves;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class OnRecreateCardModsPatches {
    @SpirePatch2(clz = LoadPlayerSaves.class, method = "Postfix")
    public static class RecreateCards {
        @SpirePostfixPatch
        public static void plz(AbstractPlayer p) {
            for (AbstractCard c : p.masterDeck.group) {
                if (c instanceof OnRecreateCardModsCard) {
                    ((OnRecreateCardModsCard) c).onRecreate();
                }
            }
        }
    }
}
