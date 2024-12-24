package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
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
            if (CardModifierManager.hasModifier(card, GearMod.ID))
                Wiz.atb(new DiscardToHandAction(card));
        }

        isDone = true;
    }
}
