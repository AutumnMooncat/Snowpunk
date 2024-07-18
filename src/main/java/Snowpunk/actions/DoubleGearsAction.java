package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class DoubleGearsAction extends AbstractGameAction {

    public static final String ID = makeID("DoubleGears");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    AbstractPlayer player;
    boolean hat, free;
    ArrayList<AbstractCard> gearCards, nonGearCards;
    AbstractCard cardPlayed;

    public DoubleGearsAction(AbstractCard cardPlayed) {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;

        gearCards = new ArrayList<>();
        nonGearCards = new ArrayList<>();
        this.cardPlayed = cardPlayed;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (CardModifierManager.hasModifier(c, GearMod.ID)) {
                    GearMod gears = (GearMod) CardModifierManager.getModifiers(c, GearMod.ID).get(0);
                    if (gears.amount > 0)
                        gearCards.add(c);
                    else
                        nonGearCards.add(c);
                } else
                    nonGearCards.add(c);
            }

            player.hand.group.removeAll(nonGearCards);
            if (player.hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            }

            if (player.hand.size() <= 1) {
                isDone = true;
                for (AbstractCard c : player.hand.group)
                    doubleGears(c);
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                doubleGears(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : nonGearCards)
            player.hand.addToTop(c);

        player.hand.refreshHandLayout();
    }

    private void doubleGears(AbstractCard card) {
        int numGears = 0;
        if (CardModifierManager.hasModifier(card, GearMod.ID)) {
            GearMod gears = (GearMod) CardModifierManager.getModifiers(card, GearMod.ID).get(0);
            numGears = gears.amount;
            if (numGears > 0) {
                CardModifierManager.addModifier(card, new GearMod(numGears));
                card.superFlash();
                card.applyPowers();
            }
        }
    }
}
