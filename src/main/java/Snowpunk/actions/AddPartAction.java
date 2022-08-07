package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cardmods.MkMod;
import Snowpunk.cards.interfaces.OnTinkeredCard;
import Snowpunk.cards.parts.AbstractPartCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

import static Snowpunk.SnowpunkMod.makeID;

public class AddPartAction extends AbstractGameAction {
    public static final String ID = makeID("Tinker");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    AbstractCard card;
    int options = 3;
    boolean randomCard;
    boolean randomlyTinker;

    public AddPartAction() {
        this(false, false);
    }

    public AddPartAction(AbstractCard card) {
        this(card, false);
    }

    public AddPartAction(AbstractCard card, boolean randomlyTinker) {
        this.card = card;
        this.randomlyTinker = randomlyTinker;
    }

    public AddPartAction(AbstractCard card, int options, boolean randomlyTinker) {
        this.card = card;
        this.options = options;
        this.randomlyTinker = randomlyTinker;
    }

    public AddPartAction(boolean randomCard, boolean randomlyTinker) {
        this.randomCard = randomCard;
        this.randomlyTinker = randomlyTinker;
    }

    @Override
    public void update() {
        //If we have a pre-specified card, heat it
        if (card != null) {
            if (randomlyTinker) {
                giveRandomPart(card);
            } else {
                pickPartsForCard(card);
            }

        } else {
            if (Wiz.adp().hand.group.stream().anyMatch(AddPartAction::acceptsAPart)) {
                if (randomCard) {
                    CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard c : Wiz.adp().hand.group) {
                        if (acceptsAPart(c)) {
                            validCards.addToTop(c);
                        }
                    }
                    if (randomlyTinker) {
                        giveRandomPart(validCards.getRandomCard(true));
                    } else {
                        pickPartsForCard(validCards.getRandomCard(true));
                    }
                } else {
                    HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                    ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                    for (AbstractCard c : Wiz.adp().hand.group) {
                        AbstractCard copy = c.makeStatEquivalentCopy();
                        cardMap.put(copy, c);
                        selectionGroup.add(copy);
                    }

                    Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, 1, TEXT[0], false, AddPartAction::acceptsAPart, cards -> {
                        for (AbstractCard c : cards) {
                            if (randomlyTinker) {
                                giveRandomPart(cardMap.get(c));
                            } else {
                                pickPartsForCard(cardMap.get(c));
                            }

                        }
                    }));
                }

            }
        }
        this.isDone = true;
    }

    //TODO - Lifted directly, needs alteration
    private static AbstractCard.CardRarity rollRarity() {
        int roll = AbstractDungeon.cardRng.random(99);
        int rareRate = 3;
        if (roll < rareRate) {
            return AbstractCard.CardRarity.RARE;
        } else {
            return roll < 40 ? AbstractCard.CardRarity.UNCOMMON : AbstractCard.CardRarity.COMMON;
        }
    }

    private static CardGroup getValidParts(AbstractCard card) {
        CardGroup validParts = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractPartCard pc : SnowpunkMod.parts) {
            if (pc.getFilter().test(card)) {
                AbstractPartCard copy = (AbstractPartCard) pc.makeCopy();
                copy.prepForSelection(card);
                validParts.addToTop(copy);
            }
        }
        return validParts;
    }

    private void pickPartsForCard(AbstractCard card) {
        CardGroup validParts = getValidParts(card);
        if (!validParts.isEmpty()) {
            ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
            if (validParts.size() <= options) {
                cardsToPick.addAll(validParts.group);
            } else {
                for (int i = 0; i < options; i++) {
                    AbstractCard c = validParts.getRandomCard(true);
                    validParts.removeCard(c);
                    cardsToPick.add(c);
                }
            }
            Wiz.att(new BetterSelectCardsCenteredAction(cardsToPick, 1, TEXT[1]+card.name+TEXT[2], false, crd -> true, parts -> {
                for (AbstractCard part : parts) {
                    if (part instanceof AbstractPartCard) {
                        applyPart(card, (AbstractPartCard) part);
                    }
                }
            }));
        }
    }

    private static void giveRandomPart(AbstractCard card) {
        CardGroup validParts = getValidParts(card);
        if (!validParts.isEmpty()) {
            AbstractCard part = validParts.getRandomCard(true);
            if (part instanceof AbstractPartCard) {
                applyPart(card, (AbstractPartCard) part);
            }
        }
    }

    private static boolean acceptsAPart(AbstractCard card) {
        return SnowpunkMod.parts.stream().anyMatch(part -> part.getFilter().test(card));
    }

    private static void applyPart(AbstractCard card, AbstractPartCard part) {
        part.apply(card);
        if (card instanceof OnTinkeredCard) {
            ((OnTinkeredCard) card).onTinkered(part);
        }
        //If part isn't as Epiphany...
        CardModifierManager.addModifier(card, new MkMod(1));
    }

    private int getAmountOfOptions() {
        //Relics to boost options, and also can boost for testing
        //return 8;
        return options;
    }
}
