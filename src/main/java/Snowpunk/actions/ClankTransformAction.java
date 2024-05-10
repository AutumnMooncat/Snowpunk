package Snowpunk.actions;

import Snowpunk.cardmods.ClankTransformOnPlay;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static Snowpunk.util.Wiz.adp;

public class ClankTransformAction extends AbstractGameAction {
    private static final float PADDING;
    private AbstractCard card;

    public ClankTransformAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        AbstractCard newCard = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK);
        if (newCard != null) {
            newCard = newCard.makeCopy();
            newCard.hb = card.hb;
            newCard.current_x = card.current_x;
            newCard.current_y = card.current_y;
            newCard.target_x = card.target_x;
            newCard.target_y = card.target_y;
            CardModifierManager.addModifier(newCard, new ClankTransformOnPlay());

            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(newCard));

            card.purgeOnUse = true;
            card.current_x = card.current_y = card.target_y = card.target_x = -1000;

            AbstractDungeon.player.limbo.group.remove(card);
            AbstractDungeon.player.hand.group.remove(card);
            AbstractDungeon.player.discardPile.group.remove(card);
            AbstractDungeon.player.drawPile.group.remove(card);
            AbstractDungeon.player.exhaustPile.group.remove(card);
        }
        isDone = true;
    }

    private ArrayList<AbstractCard> getTransmutationCandidates(AbstractCard oldCard) {
        ArrayList<AbstractCard> targets = new ArrayList<>();
        ArrayList<AbstractCard> group = getPool(oldCard);
        for (AbstractCard candidate : group) {
            if (!candidate.hasTag(AbstractCard.CardTags.HEALING) && !candidate.cardID.equals(oldCard.cardID)) {
                targets.add(candidate.makeCopy());
            }
        }
        return targets;
    }

    private AbstractCard getRandomCard(ArrayList<AbstractCard> targets) {
        if (targets.isEmpty()) {
            return null;
        }
        return targets.get(AbstractDungeon.cardRandomRng.random(targets.size() - 1));
    }

    private ArrayList<AbstractCard> getPool(AbstractCard oldCard) {
        int rarityValue = AbstractDungeon.cardRandomRng.random(1, 4);
        switch (rarityValue) {
            default:
                ArrayList<AbstractCard> group = AbstractDungeon.srcCommonCardPool.group;
                group.remove(oldCard);
                return group;
            case 2:
                ArrayList<AbstractCard> group2 = AbstractDungeon.srcUncommonCardPool.group;
                group2.remove(oldCard);
                return group2;
            case 3:
                ArrayList<AbstractCard> group3 = AbstractDungeon.srcRareCardPool.group;
                group3.remove(oldCard);
                return group3;
        }
    }

    private void evaporateCard(AbstractCard evapCard) {
        if (AbstractDungeon.player.hoveredCard == evapCard) {
            AbstractDungeon.player.releaseCard();
        }
        AbstractDungeon.actionManager.removeFromQueue(evapCard);
        evapCard.unhover();
        evapCard.untip();
        evapCard.stopGlowing();

        adp().hand.group.remove(evapCard);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(evapCard));
        EvaporatePanel.evaporatePile.addToTop(evapCard);
        AbstractDungeon.player.onCardDrawOrDiscard();
        EvaporatePanelPatches.EvaporateField.evaporate.set(evapCard, false);
        evapCard.exhaustOnUseOnce = false;
        evapCard.dontTriggerOnUseCard = false;
        Wiz.atb(new HandCheckAction());
    }

    static {
        PADDING = 25.0F * Settings.scale;// 17
    }
}