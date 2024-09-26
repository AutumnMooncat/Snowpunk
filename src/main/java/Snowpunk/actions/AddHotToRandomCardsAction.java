package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class AddHotToRandomCardsAction extends AbstractGameAction {

    boolean hotOnly;

    public AddHotToRandomCardsAction(int numCards) {
        this(numCards, true);
    }

    public AddHotToRandomCardsAction(int numCards, boolean hotOnly) {
        amount = numCards;
        this.hotOnly = hotOnly;
    }

    public void update() {
        if (Wiz.adp().hand.group.size() <= 0) {
            isDone = true;
            return;
        }
        CardGroup hotGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c.cost > -2 && (CardTemperatureFields.getCardHeat(c) > 0 || !hotOnly))
                hotGroup.addToTop(c);
        }

        hotGroup.shuffle();

        for (int i = 0; i < amount && i < hotGroup.group.size(); i++)
            CardTemperatureFields.addHeat(hotGroup.group.get(i), CardTemperatureFields.HOT);
        isDone = true;
    }
}
