package Snowpunk.actions;

import Snowpunk.cardmods.ChillMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

import static Snowpunk.SnowpunkMod.makeID;

public class CryogenizerAction extends AbstractGameAction {
    public static final String ID = makeID("Cryo");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> nonTargeted = new ArrayList();

    public CryogenizerAction(int chill) {
        this.actionType = ActionType.CARD_MANIPULATION;
        amount = chill;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            ArrayList<AbstractCard> targetedCards = new ArrayList<>();
            for (AbstractCard c : p.hand.group) {
                if (c.target == AbstractCard.CardTarget.ENEMY)
                    targetedCards.add(c);
            }

            if (targetedCards.size() == 0) {
                this.isDone = true;
                return;
            }

            if (targetedCards.size() == 1) {
                for (AbstractCard c : targetedCards) {
                    CardModifierManager.addModifier(c, new ChillMod(amount));
                    c.superFlash();
                    c.applyPowers();
                }
                this.isDone = true;
                return;
            }

            for (AbstractCard c : p.hand.group) {
                if (c.target != AbstractCard.CardTarget.ENEMY)
                    nonTargeted.add(c);
            }

            p.hand.group.removeAll(nonTargeted);
            if (p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                CardModifierManager.addModifier(c, new ChillMod(amount));
                c.superFlash();
                c.applyPowers();
                p.hand.addToTop(c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : nonTargeted)
            p.hand.addToTop(c);
    }
}
