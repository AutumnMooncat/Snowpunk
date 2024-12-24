package Snowpunk.actions;

import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static Snowpunk.SnowpunkMod.makeID;

public class ChooseCardToEvaporateAction extends AbstractGameAction {
    public static final String ID = makeID("Evaporate");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public ChooseCardToEvaporateAction(int num) {
        amount = num;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (AbstractDungeon.player.hand.size() == 0) {
                isDone = true;
                return;
            }
            if (AbstractDungeon.player.hand.size() <= amount) {
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                    EvaporatePanel.DelayedEvaporate(c);
                isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                EvaporatePanel.DelayedEvaporate(c);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        isDone = true;
        tickDuration();
    }

    private void pickFromGroup(CardGroup group) {

    }
}
