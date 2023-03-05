package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

import static Snowpunk.SnowpunkMod.makeID;

public class ModCardTempAction extends AbstractGameAction {
    AbstractCard card;
    boolean random;
    int heat;
    AbstractPlayer player;
    private ArrayList<AbstractCard> noModTemps;
    public static final String ID = makeID("ModTemp");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public ModCardTempAction(AbstractCard card, int heat) {
        this.card = card;
        this.heat = heat;
        player = AbstractDungeon.player;
    }

    public ModCardTempAction(int cardsToChoose, int heat, boolean random) {
        this.amount = cardsToChoose;
        this.heat = heat;
        this.random = random;
        player = AbstractDungeon.player;
        duration = startDuration = Settings.ACTION_DUR_FAST;

        noModTemps = new ArrayList();
    }

    @Override
    public void update() {
        //Immediately return if trying to add 0 heat
        if (heat == 0) {
            this.isDone = true;
            return;
        }

        //If we have a pre-specified card, heat it
        if (card != null) {
            CardTemperatureFields.addHeat(card, heat);
            isDone = true;
        } else if (amount != 0) {
            //Assemble all valid cards based on if they can actually accept the change in heat
            CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : Wiz.adp().hand.group) {
                /*if (heat > 0 && CardTemperatureFields.getCardHeat(c) != 2) {
                    validCards.addToTop(c);
                } else if (heat < 0 && CardTemperatureFields.getCardHeat(c) != -2 && !(CardTemperatureFields.getCardHeat(c) == 1 && CardModifierManager.hasModifier(c, EverburnMod.ID))) {
                    validCards.addToTop(c);
                }*/
                if (CardTemperatureFields.canModTemp(c, heat))
                    validCards.addToTop(c);
            }
            //If we have less valid cards than cards to modify, we can just hit all of them
            if (amount == -1 || amount >= validCards.size()) {
                for (AbstractCard c : validCards.group) {
                    CardTemperatureFields.addHeat(c, heat);
                }
                isDone = true;
            } else if (random) {
                //If not, and we are choosing randomly, remove them from the list as we go to not double up
                for (int i = 0 ; i < amount ; i++) {
                    AbstractCard c = validCards.getRandomCard(true);
                    validCards.removeCard(c);
                    CardTemperatureFields.addHeat(c, heat);
                }
                isDone = true;
            } else {
                //Failing all else, open a selection screen for the player using copied cards to not screw with the cards in hand
                /*HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();

                for (AbstractCard c : validCards.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
                }

                Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, amount, "", false, card -> true, cards -> {
                    for (AbstractCard c : cards) {
                        CardTemperatureFields.addHeat(cardMap.get(c), heat);
                    }
                }));*/
                chooseUpdate();
            }
        }
    }

    public void chooseUpdate() {
        if (duration == startDuration) {
            for (AbstractCard c : player.hand.group) {
                if (!CardTemperatureFields.canModTemp(c, heat))
                    noModTemps.add(c);
            }

            player.hand.group.removeAll(noModTemps);
            if (player.hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            }

            int index;
            switch (heat) {
                case 1:
                    index = 2;
                    break;
                case -1:
                    index = 1;
                    break;
                default:
                    if (heat > 1)
                        index = 3;
                    else
                        index = 0;
                    break;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[index], amount, false, false, false, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                CardTemperatureFields.addHeat(c, heat);
                AbstractDungeon.player.hand.addToTop(c);
                //Wiz.att(new ModCardTempAction(c, heat));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : noModTemps)
            player.hand.addToTop(c);

        player.hand.refreshHandLayout();
    }
}
