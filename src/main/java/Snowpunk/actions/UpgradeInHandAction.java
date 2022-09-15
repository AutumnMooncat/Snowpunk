package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class UpgradeInHandAction extends AbstractGameAction {
    //  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Tinker");// 14;
    //public static final String[] TEXT = uiStrings.TEXT;// 15;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList();
    private int timesToUpgrade;

    public UpgradeInHandAction(int numCards) {
        this(numCards, 1);
    }

    public UpgradeInHandAction(int numCards, int timesToUpgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        amount = numCards;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.timesToUpgrade = timesToUpgrade;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
            Iterator var2 = p.hand.group.iterator();
            while (var2.hasNext()) {
                c = (AbstractCard) var2.next();
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }

            if (upgradableCards.size() <= amount) {// 33
                var1 = upgradableCards.iterator();// 34

                while (var1.hasNext()) {
                    c = (AbstractCard) var1.next();
                    if (c.canUpgrade()) {// 35
                        upgrade(c);
                    }
                }

                this.isDone = true;// 41
                return;// 42
            }

            var1 = this.p.hand.group.iterator();// 46

            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                if (!c.canUpgrade()) {// 47
                    this.cannotUpgrade.add(c);// 48
                }
            }

            if (upgradableCards.size() == 0) {// 53
                this.isDone = true;// 54
                return;// 55
            }

            this.p.hand.group.removeAll(this.cannotUpgrade);// 72
            if (this.p.hand.group.size() > 1) {// 74
                AbstractDungeon.handCardSelectScreen.open(/*amount==1?TEXT[5]:(TEXT[6] + amount + TEXT[7]*/"Think", amount, false, false, false, true);// 75
                this.tickDuration();// 76
                return;// 77
            }
/*
            if (this.p.hand.group.size() == 1) {// 78
                this.p.hand.getTopCard().upgrade();// 79
                this.p.hand.getTopCard().superFlash();// 80
                this.returnCards();// 81
                this.isDone = true;// 82
            }*/
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();// 88

            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                upgrade(c);
                this.p.hand.addToTop(c);// 92
            }

            this.returnCards();// 95
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
            this.isDone = true;// 98
        }

        this.tickDuration();// 101
    }// 102

    private void returnCards() {
        Iterator var1 = this.cannotUpgrade.iterator();// 105

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            this.p.hand.addToTop(c);// 106
        }

        this.p.hand.refreshHandLayout();// 108
    }

    private void upgrade(AbstractCard card) {
        for (int i = 0; i < amount && card.canUpgrade(); i++) {
            card.upgrade();// 89
            card.superFlash();// 90
            card.applyPowers();// 91
        }
    }
}
