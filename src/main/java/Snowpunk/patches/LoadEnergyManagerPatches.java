package Snowpunk.patches;

public class LoadEnergyManagerPatches {
    /*
    @SpirePatch2(clz = CardCrawlGame.class, method = "loadPlayerSave")
    public static class LoadCorrectManager {
        @SpirePostfixPatch
        public static void fixManager(AbstractPlayer p) {
            if (p instanceof TheConductor) {
                int e = p.energy.energyMaster;
                p.energy = new HeatBasedEnergyManager(e);
            }
        }
    }*/
}
