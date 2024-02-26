package Snowpunk.actions;

import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ApplyCardModifierAction extends AbstractGameAction {
    private AbstractCardModifier mod;
    private AbstractCard card;
    private boolean hand;

    public ApplyCardModifierAction(AbstractCard card, AbstractCardModifier mod) {
        this.mod = mod;
        this.card = card;
        hand = false;
    }

    public ApplyCardModifierAction(boolean hand, AbstractCardModifier mod) {
        this.mod = mod;
        this.card = null;
        this.hand = hand;
    }

    @Override
    public void update() {
        if (hand) {
            for (AbstractCard c : Wiz.adp().hand.group) {
                CardModifierManager.addModifier(c, mod);
            }
        } else {
            if (card == null)
                card = Wiz.adp().hand.getRandomCard(true);
            CardModifierManager.addModifier(card, mod);
        }
        isDone = true;
    }
}
