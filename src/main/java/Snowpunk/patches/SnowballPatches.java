package Snowpunk.patches;

import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

public class SnowballPatches {
    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CardCostPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> bePlayable(AbstractCard __instance) {
            AbstractPower power = Wiz.adp().getPower(SnowballPower.POWER_ID);
            if (power != null) {
                if (EnergyPanel.totalCount + power.amount >= __instance.costForTurn) {
                    return SpireReturn.Return(true);
                }
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(EnergyPanel.class, "totalCount");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class ConsumeSnowPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void useSnow(AbstractPlayer __instance, AbstractCard c) {
            if (__instance.hasPower(SnowballPower.POWER_ID) && c.costForTurn > 0) {
                int delta = c.costForTurn - EnergyPanel.getCurrentEnergy();
                if (delta > 0)
                    Wiz.att(new ReducePowerAction(__instance, __instance, SnowballPower.POWER_ID, delta));
            }
        }
        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(EnergyManager.class, "use");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }

        @SpireInsertPatch(locator = Locator2.class)
        public static void fixSCostShenanigans(AbstractPlayer __instance, AbstractCard c) {
            if (SCostFieldPatches.SCostField.isSCost.get(c)) {
                if ((!c.freeToPlayOnce || AltCostPatch.AltCostField.usingAltCost.get(c)) && (__instance.hasPower(SnowballPower.POWER_ID))) {
                    int snow = __instance.getPower(SnowballPower.POWER_ID).amount;
                    __instance.loseEnergy(snow);
                }
            }
        }
        public static class Locator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractCard.class, "freeToPlay");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
