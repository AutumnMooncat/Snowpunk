package Snowpunk.augments;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import Snowpunk.SnowpunkMod;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class AugmentHelper {
    public static void register() {
        CardAugmentsMod.registerMod(SnowpunkMod.modID, CardCrawlGame.languagePack.getUIString(makeID("Crossover")).TEXT[0]);
        new AutoAdd(SnowpunkMod.modID)
                .packageFilter("Snowpunk.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, SnowpunkMod.modID);});
    }
}
