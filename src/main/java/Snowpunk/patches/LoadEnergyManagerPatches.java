package Snowpunk.patches;

import Snowpunk.TheConductor;
import Snowpunk.util.HeatBasedEnergyManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class LoadEnergyManagerPatches {
    @SpirePatch2(clz = CardCrawlGame.class, method = "loadPlayerSave")
    public static class LoadCorrectManager {
        @SpirePostfixPatch
        public static void fixManager(AbstractPlayer p) {
            if (p instanceof TheConductor) {
                int e = p.energy.energyMaster;
                p.energy = new HeatBasedEnergyManager(e);
            }
        }
    }
}
