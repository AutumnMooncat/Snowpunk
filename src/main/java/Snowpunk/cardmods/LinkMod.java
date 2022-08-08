package Snowpunk.cardmods;

import Snowpunk.actions.RepeatCardAction;
import Snowpunk.actions.ResetLinkedPlayStatusAction;
import Snowpunk.patches.CardTemperatureFields;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;
import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

@AbstractCardModifier.SaveIgnore
public class LinkMod extends AbstractCardModifier {
    public static String ID = makeID(LinkMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    public static final Logger logger = LogManager.getLogger(LinkMod.class.getName());

    public ArrayList<AbstractCard> linkedCards;
    public AbstractCard thisCard;
    public boolean inPlay = false;

    public LinkMod() {
        this(null, null);
    }

    public LinkMod(ArrayList<AbstractCard> cardsToLink, AbstractCard card) {
        linkedCards = new ArrayList<>();
        thisCard = card;
        inPlay = false;
        if (cardsToLink != null && cardsToLink.size() > 0 && card != null) {
            for (AbstractCard cardToLink : cardsToLink) {
                if (cardToLink != card && !linkedCards.contains(cardToLink))
                    linkedCards.add(cardToLink);
            }
        }
        updateLinked(card);
    }

    @Override
    public void onDrawn(AbstractCard card) {
        AbstractPlayer player = AbstractDungeon.player;
        updateLinked(card);
        searchAndMoveToGroup(player.hand);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractPlayer player = AbstractDungeon.player;
        if (target == null)
            target = AbstractDungeon.getRandomMonster();
        for (AbstractCard linkedCard : linkedCards) {
            if (player.hand.contains(linkedCard)) {
                if (target instanceof AbstractMonster)
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(linkedCard, (AbstractMonster) target, 0, true, true));
                else
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(linkedCard, false));
            }
        }
        //inPlay = true;
        //AbstractDungeon.actionManager.addToBottom(new ResetLinkedPlayStatusAction(card, linkedCards));
    }

    private boolean checkQueueForResetLinkAction() {
        boolean resetExists = false;
        for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
            if (action instanceof ResetLinkedPlayStatusAction)
                resetExists = true;
        }
        return resetExists;
    }

//    @Override
//    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
//        if(linkedCards.contains(otherCard) && !inPlay){
//            AbstractDungeon.actionManager.addToBottom(new UseCardAction(card));
//            inPlay = true;
//        }
//    }// 168

    private void searchAndMoveToGroup(CardGroup pile) {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard linkedCard : linkedCards) {
            if (player.hand.size() >= BaseMod.MAX_HAND_SIZE)
                return;
            if (player.hand.contains(linkedCard) && pile != player.hand) {
                player.hand.removeCard(linkedCard);
                updateLinked(linkedCard);
                pile.addToTop(linkedCard);
            } else if (player.drawPile.contains(linkedCard) && pile != player.drawPile) {
                player.drawPile.removeCard(linkedCard);
                updateLinked(linkedCard);
                pile.addToTop(linkedCard);
            } else if (player.discardPile.contains(linkedCard) && pile != player.discardPile) {
                player.discardPile.removeCard(linkedCard);
                updateLinked(linkedCard);
                pile.addToTop(linkedCard);
            } else if (player.limbo.contains(linkedCard) && pile != player.limbo) {
                player.limbo.removeCard(linkedCard);
                updateLinked(linkedCard);
                pile.addToTop(linkedCard);
            } else if (player.exhaustPile.contains(linkedCard) && pile != player.exhaustPile) {
                player.exhaustPile.removeCard(linkedCard);
                updateLinked(linkedCard);
                unExhaustEffects(linkedCard);
                pile.addToTop(linkedCard);
            }
        }
    }

    private void updateLinked(AbstractCard card) {
        card.setCostForTurn(card.cost + linkedCards.size());
        //card.cardsToPreview = linkedCards.get(0).makeCopy();
        card.initializeDescription();
    }

    public static void Link(ArrayList<AbstractCard> cardsToLink, AbstractCard card) {
        logger.info("Linking " + getLinksToString(cardsToLink) + " to " + card.name);
        ArrayList<AbstractCardModifier> linkMod = getModifiers(card, ID);
//        if(linkMod.size() > 0 && linkMod.get(0) instanceof LinkMod)
//        {
//            logger.info("Found Existing Link");
//            LinkMod linkModifier = (LinkMod)linkMod.get(0);
//            linkModifier.Append(cardsToLink, card);
//        }
        if (CardModifierManager.hasModifier(card, LinkMod.ID)) {
            logger.info("Found Existing Link");
            LinkMod linkModifier = (LinkMod) CardModifierManager.getModifiers(card, LinkMod.ID).get(0);
            linkModifier.Append(cardsToLink, card);
        } else
            addModifier(card, new LinkMod(cardsToLink, card));
    }

    public static void Link(AbstractCard card1, AbstractCard card2) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(card1);
        Link(cards, card2);
    }

    private void Append(ArrayList<AbstractCard> cardsToLink, AbstractCard self) {
        logger.info("Appending " + getLinksToString(cardsToLink) + " to " + self.name);
        ArrayList<AbstractCard> fullListOfLinkedCards = getFullLinkedList(cardsToLink, self);
        logger.info("Full list of links: " + getLinksToString(fullListOfLinkedCards));
        for (AbstractCard card : fullListOfLinkedCards) {
            ArrayList<AbstractCardModifier> linkMod = getModifiers(card, ID);
            if (linkMod.size() > 0 && linkMod.get(0) instanceof LinkMod) {
                LinkMod linkModifier = (LinkMod) linkMod.get(0);
                ArrayList<AbstractCard> tempLink = (ArrayList<AbstractCard>) fullListOfLinkedCards.clone();
                if (tempLink.contains(card))
                    tempLink.remove(card);
                linkModifier.linkedCards.clear();
                linkModifier.linkedCards = tempLink;
                linkModifier.updateLinked(card);
            } else {
                addModifier(card, new LinkMod(fullListOfLinkedCards, card));
            }
        }
    }

    private ArrayList<AbstractCard> getFullLinkedList(ArrayList<AbstractCard> cardsToLink, AbstractCard card) {
        ArrayList<AbstractCard> fullList = new ArrayList<>();
        fullList.add(card);
        for (AbstractCard linkCard : cardsToLink) {
            if (!fullList.contains(linkCard))
                fullList.add(linkCard);
            if (CardModifierManager.hasModifier(linkCard, LinkMod.ID)) {
                for (AbstractCard linkCardLinks : ((LinkMod) CardModifierManager.getModifiers(linkCard, LinkMod.ID).get(0)).linkedCards) {
                    if (!fullList.contains(linkCardLinks))
                        fullList.add(linkCardLinks);
                }
            }
        }
        return fullList;
    }

    private void unExhaustEffects(AbstractCard card) {
        card.unhover();
        card.unfadeOut();
        card.lighten(true);
        card.setAngle(0.0F);
        card.fadingOut = false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return TEXT[0] + getLinksToString(linkedCards) + TEXT[2] + rawDescription;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    private static String getLinksToString(ArrayList<AbstractCard> cards) {
        String returnString = "";
        for (AbstractCard card : cards) {
            returnString += card.name;
            if (cards.indexOf(card) < cards.size() - 1 && cards.size() > 2)
                returnString += ", ";
            if (cards.indexOf(card) == cards.size() - 2)
                returnString += TEXT[1];
        }
        return returnString;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new LinkMod(linkedCards, thisCard);
    }
}
