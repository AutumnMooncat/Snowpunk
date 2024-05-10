package Snowpunk.actions;

import Snowpunk.cards.DirtyDan;
import Snowpunk.cards.PinheadLarry;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.HashSet;
import java.util.Set;

public class DirtyDanAction extends AbstractGameAction {

    private final Set<AbstractCard> cardsApplied = new HashSet<>();

    public DirtyDanAction() {
    }

    @Override
    public void update() {
        if (Wiz.adp().cardInUse instanceof DirtyDan && cardsApplied.add(Wiz.adp().cardInUse)) {
            Wiz.adp().cardInUse.modifyCostForCombat(-1);
            Wiz.adp().cardInUse.superFlash();
        }
        if (Wiz.adp().cardInUse instanceof PinheadLarry && cardsApplied.add(Wiz.adp().cardInUse))
            ((PinheadLarry) Wiz.adp().cardInUse).DirtyDans--;
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c instanceof DirtyDan && cardsApplied.add(c)) {
                c.modifyCostForCombat(-1);
                c.superFlash();
            }
            if (c instanceof PinheadLarry && cardsApplied.add(c))
                ((PinheadLarry) c).DirtyDans--;
        }
        for (AbstractCard c : Wiz.adp().limbo.group) {
            if (c instanceof DirtyDan && cardsApplied.add(c)) {
                c.modifyCostForCombat(-1);
                c.superFlash();
            }
            if (c instanceof PinheadLarry && cardsApplied.add(c))
                ((PinheadLarry) c).DirtyDans--;
        }
        for (AbstractCard c : Wiz.adp().discardPile.group) {
            if (c instanceof DirtyDan && cardsApplied.add(c)) {
                c.modifyCostForCombat(-1);
                c.superFlash();
            }
            if (c instanceof PinheadLarry && cardsApplied.add(c))
                ((PinheadLarry) c).DirtyDans--;
        }
        for (AbstractCard c : Wiz.adp().drawPile.group) {
            if (c instanceof DirtyDan && cardsApplied.add(c)) {
                c.modifyCostForCombat(-1);
                c.superFlash();
            }
            if (c instanceof PinheadLarry && cardsApplied.add(c))
                ((PinheadLarry) c).DirtyDans--;
        }
        for (AbstractCard c : Wiz.adp().exhaustPile.group) {
            if (c instanceof DirtyDan && cardsApplied.add(c)) {
                c.modifyCostForCombat(-1);
                c.superFlash();
            }
            if (c instanceof PinheadLarry && cardsApplied.add(c))
                ((PinheadLarry) c).DirtyDans--;
        }
        for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
            if (c instanceof DirtyDan && cardsApplied.add(c)) {
                c.modifyCostForCombat(-1);
                c.superFlash();
            }
            if (c instanceof PinheadLarry && cardsApplied.add(c))
                ((PinheadLarry) c).DirtyDans--;
        }
        this.isDone = true;
    }
}
