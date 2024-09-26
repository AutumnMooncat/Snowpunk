package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.GoldenTicket;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class MakeCopyInHandAction extends AbstractGameAction {

    public static final String ID = makeID("Copy");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    AbstractPlayer player;
    ArrayList<AbstractCard> removedCards;
    int numHats;

    public MakeCopyInHandAction(int hats) {
        this.actionType = ActionType.CARD_MANIPULATION;

        removedCards = new ArrayList<>();
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;
        numHats = hats;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (AbstractCard c : player.hand.group) {
                if (c instanceof GoldenTicket)
                    removedCards.add(c);
            }

            Wiz.adp().hand.group.removeAll(removedCards);

            if (player.hand.size() == 0) {
                this.isDone = true;
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.player.hand.addToTop(c);

                AbstractCard newCard = c.makeStatEquivalentCopy();

                if (numHats > 0)
                    CardModifierManager.addModifier(newCard, new HatMod(numHats));
                returnCards();
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(newCard));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : removedCards)
            Wiz.adp().hand.addToTop(c);

        Wiz.adp().hand.refreshHandLayout();
    }
}
