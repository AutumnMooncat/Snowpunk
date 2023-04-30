package Snowpunk.actions;

import Snowpunk.patches.EvaporatePanelPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvaporateRandomCardsAction extends AbstractGameAction {
    private AbstractPlayer p;

    public EvaporateRandomCardsAction(int num) {
        this.amount = num;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.group.size() <= 0) {
                this.isDone = true;
                return;
            }
            CardGroup evaporate = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : p.hand.group)
                evaporate.addToTop(c);

            evaporate.shuffle();

            for (int i = 0; i < amount && i < evaporate.group.size(); i++)
                EvaporatePanelPatches.EvaporateField.evaporate.set(evaporate.group.get(i), true);
            this.isDone = true;
            return;
        }
        tickDuration();
    }
}
