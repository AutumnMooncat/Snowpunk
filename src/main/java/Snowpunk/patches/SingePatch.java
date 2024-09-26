package Snowpunk.patches;

import Snowpunk.powers.SingePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SingePatch {
    /*@SpirePatch2(clz = EmptyDeckShuffleAction.class, method = "update")
    public static class RemoveSinge {
        @SpireInsertPatch(
                rloc = 2
        )
        public static void update(EmptyDeckShuffleAction __instance) {
            for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters) {
                SingePower singe = (SingePower) monster.getPower(SingePower.POWER_ID);
                if(singe != null){
                    singe.flash();
                    int reduce = singe.amount;
                    if(singe.amount % 2 == 1)
                        reduce++;
                    reduce /=2;
                    Wiz.atb(new ReducePowerAction(monster, monster, singe, reduce));
                }
            }
        }
    }*/

}
