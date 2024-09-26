package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class AddHotToRandomEvaporatedCardAction extends AbstractGameAction {

    public AddHotToRandomEvaporatedCardAction(int numCards) {
        amount = numCards;
    }

    public void update() {
        if (EvaporatePanel.evaporatePile.group.size() <= 0) {
            isDone = true;
            return;
        }
        CardGroup hotGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : EvaporatePanel.evaporatePile.group)
            hotGroup.addToTop(c);

        hotGroup.shuffle();

        for (int i = 0; i < amount && i < hotGroup.group.size(); i++)
            CardTemperatureFields.addHeat(hotGroup.group.get(i), CardTemperatureFields.HOT);
        isDone = true;
    }
}
