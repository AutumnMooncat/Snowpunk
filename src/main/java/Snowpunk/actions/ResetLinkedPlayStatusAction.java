package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ResetLinkedPlayStatusAction extends AbstractGameAction {
    private AbstractCard card;
    public ArrayList<AbstractCard> linkedCards;

    public static final Logger logger = LogManager.getLogger(ResetLinkedPlayStatusAction.class.getName());

    public ResetLinkedPlayStatusAction(AbstractCard card, ArrayList<AbstractCard> linkedCards) {
        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.WAIT;
        this.card = card;
        this.linkedCards = linkedCards;
    }

    public void update() {
/*        logger.info("Current Card queue: ");
        for(CardQueueItem cardQueueItem : AbstractDungeon.actionManager.cardQueue){
            logger.info(cardQueueItem.card.name);
            if(linkedCards.contains(cardQueueItem.card)){
                logger.info("Found Linked card, so ending action");
                isDone = true;
                return;
            }
        }
        for(AbstractCard linkedCard: linkedCards){
            if (CardModifierManager.hasModifier(linkedCard, LinkMod.ID)) {
                LinkMod linkModifier = (LinkMod) CardModifierManager.getModifiers(linkedCard, LinkMod.ID).get(0);
                linkModifier.inPlay = false;
            }
        }

        if (CardModifierManager.hasModifier(card, LinkMod.ID)) {
            LinkMod linkModifier = (LinkMod) CardModifierManager.getModifiers(card, LinkMod.ID).get(0);
            linkModifier.inPlay = false;
        }*/
        isDone = true;
/*
        if (AbstractDungeon.actionManager.cardQueue.size() > 0) {
            AbstractDungeon.actionManager.actions.remove(this);
            AbstractDungeon.actionManager.addToBottom(this);
            return;
        }
        else if (CardModifierManager.hasModifier(card, LinkMod.ID)) {
            LinkMod linkModifier = (LinkMod) CardModifierManager.getModifiers(card, LinkMod.ID).get(0);
            linkModifier.inPlay = false;
            isDone = true;
        }
        else
            isDone = true;*/
    }

}