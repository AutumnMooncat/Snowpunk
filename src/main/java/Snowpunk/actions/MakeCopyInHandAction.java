package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class MakeCopyInHandAction extends AbstractGameAction {

    public static final String ID = makeID("Copy");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    AbstractPlayer player;
    boolean upgraded;

    public MakeCopyInHandAction(boolean freeThisTurn) {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;
        this.upgraded = freeThisTurn;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (player.hand.size() == 0) {
                this.isDone = true;
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

                if (upgraded)
                    newCard.setCostForTurn(0);

                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(newCard));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
