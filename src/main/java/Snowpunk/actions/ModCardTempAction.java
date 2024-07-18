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
    boolean random, choosing = false, any;
    int heat;
    AbstractPlayer player;
    private ArrayList<AbstractCard> noModTemps;
    public static final String ID = makeID("ModTemp");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private ArrayList<AbstractCard> group;

    public ModCardTempAction(AbstractCard card, int heat) {
        this.card = card;
        this.heat = heat;
        player = AbstractDungeon.player;
    }

    public ModCardTempAction(int cardsToChoose, int heat, boolean random) {
        this(cardsToChoose, heat, random, false);
    }

    public ModCardTempAction(int cardsToChoose, int heat, boolean random, boolean anyAmount) {
        this.amount = cardsToChoose;
        this.heat = heat;
        this.random = random;
        any = anyAmount;
        player = AbstractDungeon.player;
        duration = startDuration = Settings.ACTION_DUR_FAST;

        noModTemps = new ArrayList();
    }

    public ModCardTempAction(ArrayList<AbstractCard> group, int heat) {
        this.heat = heat;
        player = AbstractDungeon.player;
        this.group = group;
        amount = 99;
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
            if (group == null)
                group = Wiz.adp().hand.group;
            for (AbstractCard c : group) {
                if (CardTemperatureFields.canModTemp(c, heat))
                    validCards.addToTop(c);
            }
            //If we have less valid cards than cards to modify, we can just hit all of them
            if (choosing)
                chooseUpdate();
            else if ((amount == -1 || amount >= validCards.size()) && !any) {
                for (AbstractCard c : validCards.group) {
                    CardTemperatureFields.addHeat(c, heat);
                }
                isDone = true;
            } else if (random) {
                //If not, and we are choosing randomly, remove them from the list as we go to not double up
                for (int i = 0; i < amount; i++) {
                    AbstractCard c = validCards.getRandomCard(true);
                    validCards.removeCard(c);
                    CardTemperatureFields.addHeat(c, heat);
                }
                isDone = true;
            } else {
                chooseUpdate();
            }
        }
    }

    public void chooseUpdate() {
        if (duration == startDuration) {
            choosing = true;
            if (amount == 0) {
                isDone = true;
                return;
            }
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
            AbstractDungeon.handCardSelectScreen.open(TEXT[index], amount, false, any, false, false, any);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                CardTemperatureFields.addHeat(c, heat);
                AbstractDungeon.player.hand.addToTop(c);
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
