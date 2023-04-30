package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.util.Wiz.adp;

public class RubeGoldbergAction extends AbstractGameAction {
    AbstractCard card;

    public RubeGoldbergAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (CardModifierManager.hasModifier(card, GearMod.ID)) {

        }
        addToTop(new ResetExhaustAction(card, true));
        isDone = true;
    }
}
