package Snowpunk.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveCardModifierAction extends AbstractGameAction {
    private AbstractCardModifier mod;
    private AbstractCard card;

    public RemoveCardModifierAction(AbstractCard card, AbstractCardModifier mod) {
        this.mod = mod;
        this.card = card;
    }

    @Override
    public void update() {
        isDone = true;
        CardModifierManager.removeSpecificModifier(card, mod, true);
    }
}
