package Snowpunk.actions;

import Snowpunk.cardmods.LinkMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.util.Wiz.adp;

public class PlayLinkedCardsInHandAction extends AbstractGameAction {

    public ArrayList<AbstractCard> linkedCards;

    public PlayLinkedCardsInHandAction() {
        linkedCards = new ArrayList<>();
    }

    @Override
    public void update() {
        for (AbstractCard card : adp().hand.group) {
            if (CardModifierManager.hasModifier(card, LinkMod.ID)) {
                if (!linkedCards.contains(card)) {
                    linkedCards.add(card);
                    addToTop(new NewQueueCardAction(card, AbstractDungeon.getRandomMonster(), false, true));
                    addToTop(new UnlimboAction(card));
                }
                addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                LinkMod linkMod = (LinkMod) CardModifierManager.getModifiers(card, LinkMod.ID).get(0);
                for (AbstractCard linkedCard : linkMod.linkedCards) {
                    if (!linkedCards.contains(linkedCard)) {
                        linkedCards.add(linkedCard);
                    }
                }
            }
        }
        isDone = true;
    }
}
