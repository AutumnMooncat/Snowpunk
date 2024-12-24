package Snowpunk.patches;

import Snowpunk.cardmods.DontIncludeInCombatMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

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
