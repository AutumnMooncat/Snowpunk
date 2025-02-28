package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.util.Wiz.adp;

public class GearCardsToHandAction extends AbstractGameAction {
    @Override
    public void update() {
        for (AbstractCard card : Wiz.adp().discardPile.group) {
            if ((CardModifierManager.hasModifier(card, GearMod.ID) && ((GearMod) CardModifierManager.getModifiers(card, GearMod.ID).get(0)).amount > 0) ||
                    (CardModifierManager.hasModifier(card, PlateMod.ID) && ((PlateMod) CardModifierManager.getModifiers(card, PlateMod.ID).get(0)).amount > 0))
                Wiz.att(new DiscardToHandAction(card));
        }

        isDone = true;
    }
}
