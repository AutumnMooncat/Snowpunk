package Snowpunk.patches;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

public class SnowballPatches {
    private static final int HEAT_PER_BALL = 0;

    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CardCostPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> bePlayable(AbstractCard __instance) {
            if (CardModifierManager.hasModifier(__instance, ClockworkMod.ID)) {
                int gears = ((ClockworkMod) CardModifierManager.getModifiers(__instance, ClockworkMod.ID).get(0)).amount;
                if (EnergyPanel.totalCount + gears >= __instance.costForTurn)
                    return SpireReturn.Return(true);

            }
            AbstractPower power = Wiz.adp().getPower(SnowballPower.POWER_ID);
            /*if (SCostFieldPatches.SCostField.isSCost.get(__instance)) {
                if (power != null && EnergyPanel.totalCount < power.amount) {
                    return SpireReturn.Return(false);
                }
            }*/
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
            if ((__instance.hasPower(SnowballPower.POWER_ID) || CardModifierManager.hasModifier(c, ClockworkMod.ID)) && c.costForTurn > 0) {
                int gears = 0;
                if (CardModifierManager.hasModifier(c, ClockworkMod.ID))
                    gears = ((ClockworkMod) CardModifierManager.getModifiers(c, ClockworkMod.ID).get(0)).amount;
                if (c.costForTurn <= gears) {
                    Wiz.att(new ApplyCardModifierAction(c, new ClockworkMod(-c.costForTurn)));
                    //__instance.energy.energy += c.costForTurn;
                    c.costForTurn = 0;
                } else {
                    if (gears > 0) {
                        Wiz.att(new ApplyCardModifierAction(c, new ClockworkMod(-gears)));
                        c.costForTurn -= gears;
                    }
                    int delta = c.costForTurn - EnergyPanel.getCurrentEnergy();
                    if (delta > 0)
                        Wiz.att(new ReducePowerAction(__instance, __instance, SnowballPower.POWER_ID, delta));
                }
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
                if ((!c.freeToPlayOnce || AltCostPatch.AltCostField.usingAltCost.get(c)) && (__instance.hasPower(SnowballPower.POWER_ID) || CardModifierManager.hasModifier(c, ClockworkMod.ID))) {
                    int snow = __instance.getPower(SnowballPower.POWER_ID).amount;
                    int gears = 0;
                    if (CardModifierManager.hasModifier(c, ClockworkMod.ID))
                        gears = ((ClockworkMod) CardModifierManager.getModifiers(c, ClockworkMod.ID).get(0)).amount;
                    int energy = EnergyPanel.totalCount;
                    if (snow > energy + gears) {
                        Wiz.atb(new ReducePowerAction(__instance, __instance, SnowballPower.POWER_ID, snow - (energy + gears)));
                        Wiz.atb(new ApplyCardModifierAction(c, new ClockworkMod(-gears)));
                    } else if (snow <= gears) {
                        Wiz.atb(new ApplyCardModifierAction(c, new ClockworkMod(-snow)));
                    } else {
                        Wiz.atb(new ApplyCardModifierAction(c, new ClockworkMod(-gears)));
                        __instance.loseEnergy(snow - gears);
                    }
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

    /*@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class SCostUseEPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void useSnow(AbstractPlayer __instance, AbstractCard c) {
            if (SCostFieldPatches.SCostField.isSCost.get(c) && !c.freeToPlay()) {
                int snow = __instance.getPower(SnowballPower.POWER_ID).amount;
                if (EnergyPanel.totalCount < snow) {
                    Wiz.att(new ReducePowerAction(__instance, __instance, SnowballPower.POWER_ID, snow - EnergyPanel.totalCount));
                }
                __instance.energy.use(snow);
            }
        }
        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractCard.class, "freeToPlay");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }*/
}
