package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

import static Snowpunk.SnowpunkMod.makeID;

public class MultiUpgradeInHandAction extends AbstractGameAction {

    public static final String ID = makeID("Upgrade");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    AbstractPlayer player;
    private ArrayList<AbstractCard> cannotUpgrade;
    private int timesToUpgrade;

    public MultiUpgradeInHandAction(int numCards, int timesToUpgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        amount = numCards;
        player = AbstractDungeon.player;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.timesToUpgrade = timesToUpgrade;
        cannotUpgrade = new ArrayList();
    }

    public void update() {
        if (this.duration == this.startDuration) {
            Iterator var1;
            var1 = player.hand.group.iterator();// 46

            while (var1.hasNext()) {
                AbstractCard c = (AbstractCard) var1.next();
                if (!c.canUpgrade()) {// 47
                    cannotUpgrade.add(c);// 48
                }
            }

            player.hand.group.removeAll(cannotUpgrade);// 72
            if (player.hand.size() == 0) {
                this.isDone = true;
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, true, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                upgrade(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
        }
        tickDuration();
    }

    private void returnCards() {
        Iterator var1 = cannotUpgrade.iterator();// 105

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            player.hand.addToTop(c);// 106
        }

        player.hand.refreshHandLayout();// 108
    }

    private void upgrade(AbstractCard card) {
        for (int i = 0; i < timesToUpgrade && card.canUpgrade(); i++) {
            card.upgrade();// 89
            card.superFlash();// 90
            card.applyPowers();// 91
        }
    }
}
