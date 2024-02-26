package Snowpunk.patches;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.relics.interfaces.ModifySnowballsRelic;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.ModifyXCostPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

public class SnowballPatches {

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Snowballs {
        private static int amount = 0;

        public static void changeSnow(int snow) {
            amount += snow;
            if (amount < 0)
                amount = 0;
        }

        public static void setSnow(int snow) {
            amount = snow;
            if (amount < 0)
                amount = 0;
        }

        public static int getPerTurn() {
            int perTurn = 0;
            if (AbstractDungeon.player instanceof TheConductor)
                perTurn++;

            for (AbstractRelic r : Wiz.adp().relics) {
                if (r instanceof ModifySnowballsRelic)
                    perTurn += ((ModifySnowballsRelic) r).modifySnow();
            }

            return perTurn;
        }

        public static void startTurn() {
            int snow = getPerTurn();
            if (snow > 0)
                Wiz.att(new GainSnowballAction(snow));
        }

        public static int getTrueAmount() {
            return amount;
        }

        public static int getEffectiveAmount() {
            int snow = amount;
            if (Wiz.adp() != null) {
                for (AbstractRelic r : Wiz.adp().relics) {
                    if (r instanceof SnowAmountModifier) {
                        snow += ((SnowAmountModifier) r).modifySnow();
                    }
                }
                for (AbstractPower pow : Wiz.adp().powers) {
                    if (pow instanceof SnowAmountModifier) {
                        snow += ((SnowAmountModifier) pow).modifySnow();
                    }
                }
            }
            return snow;
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CardCostPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> bePlayable(AbstractCard __instance) {
            //AbstractPower power = Wiz.adp().getPower(SnowballPower.POWER_ID);
            if (Snowballs.amount > 0) {
                if (EnergyPanel.totalCount + Snowballs.amount >= __instance.costForTurn) {
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
        @SpireInsertPatch(locator = ModifyXCostPatch.Locator.class)
        public static void useSnow(AbstractPlayer __instance, AbstractCard c) {
            if (Snowballs.getEffectiveAmount() > 0 && c.costForTurn > 0 && !c.purgeOnUse) {
                int delta = c.costForTurn - EnergyPanel.getCurrentEnergy();
                if (delta > 0 && !c.freeToPlayOnce)
                    Wiz.atb(new GainSnowballAction(-delta));
            }
            if (Snowballs.getEffectiveAmount() > 0 && c.costForTurn == -1 && !c.purgeOnUse) {
                c.energyOnUse += AbstractEasyCard.getSnowStatic();
                if (!c.freeToPlayOnce)
                    Wiz.atb(new GainSnowballAction(-Snowballs.getTrueAmount()));
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
                if ((!c.freeToPlayOnce || AltCostPatch.AltCostField.usingAltCost.get(c)) && (Snowballs.getEffectiveAmount() > 0/*__instance.hasPower(SnowballPower.POWER_ID)*/)) {
                    int snow = Snowballs.getEffectiveAmount();//__instance.getPower(SnowballPower.POWER_ID).amount;
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
