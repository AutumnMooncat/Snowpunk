package Snowpunk.actions;

import Snowpunk.powers.interfaces.OnCondensePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CondenseRandomCardToHandAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public CondenseRandomCardToHandAction(int amount) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.setValues(p, p, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                p.createHandIsFullDialog();
                this.isDone = true;
            } else if (EvaporatePanel.evaporatePile.isEmpty()) {
                this.isDone = true;
            } else {
                int draws = Math.min(EvaporatePanel.evaporatePile.size(), this.amount);
                if (p.hand.size() + draws > BaseMod.MAX_HAND_SIZE) {
                    draws = BaseMod.MAX_HAND_SIZE - p.hand.size();
                    p.createHandIsFullDialog();
                }
                for (int i = 0 ; i < draws ; i++) {
                    AbstractCard card = EvaporatePanel.evaporatePile.getRandomCard(true);
                    card.unfadeOut();
                    this.p.hand.addToHand(card);
                    if (p.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL) {
                        card.setCostForTurn(-9);
                    }

                    EvaporatePanel.evaporatePile.removeCard(card);

                    card.unhover();
                    card.fadingOut = false;
                    triggerOnCondense(card);
                }
            }
        }
        this.tickDuration();
    }

    private void triggerOnCondense(AbstractCard card) {
        if (Wiz.adp() != null) {
            for (AbstractPower power : Wiz.adp().powers) {
                if (power instanceof OnCondensePower)
                    ((OnCondensePower) power).onCondense(card);
            }
        }
    }
}
