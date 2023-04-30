package Snowpunk.actions;

import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import static Snowpunk.util.Wiz.adp;

public class EvaporateCardInHandAction extends AbstractGameAction {
    AbstractCard card;

    public EvaporateCardInHandAction(AbstractCard card) {
        this.card = card;
    }

    public EvaporateCardInHandAction() {
        card = null;
    }

    @Override
    public void update() {
        if (card == null) {
            if (AbstractDungeon.player.hand.size() > 0)
                card = AbstractDungeon.player.hand.getRandomCard(true);
            else {
                isDone = true;
                return;
            }
        }
        if (AbstractDungeon.player.hoveredCard == card) {
            AbstractDungeon.player.releaseCard();
        }
        AbstractDungeon.actionManager.removeFromQueue(card);
        card.unhover();
        card.untip();
        card.stopGlowing();

        adp().hand.group.remove(card);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(card));
        EvaporatePanel.evaporatePile.addToTop(card);
        AbstractDungeon.player.onCardDrawOrDiscard();
        EvaporatePanelPatches.EvaporateField.evaporate.set(card, false);
        card.exhaustOnUseOnce = false;
        card.dontTriggerOnUseCard = false;
        for (AbstractPower pow : adp().powers) {
            if (pow instanceof OnEvaporatePower) {
                ((OnEvaporatePower) pow).onEvaporate(card);
            }
        }
        Wiz.atb(new HandCheckAction());
        isDone = true;
    }
}
