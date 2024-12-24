package Snowpunk.patches;

import Snowpunk.cardmods.CargoMod;
import Snowpunk.cardmods.DontIncludeInCombatMod;
import Snowpunk.ui.EvaporatePanel;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DontIncludeInCombatPatch {

    @SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
    public static class PreventAddingToDeck {
        @SpireInsertPatch(
                rloc = 3,
                localvars = {"copy"}
        )
        public static void update(CardGroup __instance, @ByRef CardGroup[] copy) {
            ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
            for (AbstractCard c : copy[0].group) {
                if (CardModifierManager.hasModifier(c, DontIncludeInCombatMod.ID))
                    cardsToRemove.add(c);
            }
            for (AbstractCard c : cardsToRemove)
                copy[0].removeCard(c);
        }
    }
}
