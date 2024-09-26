package Snowpunk.patches;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cardmods.CondensedMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.relics.interfaces.ModifySnowballsRelic;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.ModifyXCostPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

public class HollyPatches {

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Holly {
        public static int amount = 0;
        public static final int THRESHOLD = 12;
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class DoublePlayHollyPatch {
        @SpirePostfixPatch()
        public static void checkHolly(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if (Holly.amount >= Holly.THRESHOLD && !c.purgeOnUse) {
                Holly.amount -= Holly.THRESHOLD;

                AbstractMonster m = null;
                if (monster != null)
                    m = monster;
                AbstractCard tmp = c.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = c.current_x;
                tmp.current_y = c.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (m != null)
                    tmp.calculateCardDamage(m);
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, c.energyOnUse, true, true), true);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class ClearPile {
        @SpirePrefixPatch
        public static void clear(AbstractPlayer __instance) {
            Holly.amount = 0;
            HollyUIPatches.showHolly = false;
        }
    }
}
