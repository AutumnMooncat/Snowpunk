package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddHatsToRandomCardsAction extends AbstractGameAction {

    public AddHatsToRandomCardsAction(int numCards) {
        amount = numCards;
    }

    public void update() {
        if (Wiz.adp().hand.group.size() <= 0) {
            isDone = true;
            return;
        }
        CardGroup hat = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c.cost >= -1)
                hat.addToTop(c);
        }

        hat.shuffle();

        for (int i = 0; i < amount && i < hat.group.size(); i++)
            CardModifierManager.addModifier(hat.group.get(i), new HatMod());
        isDone = true;
        return;
    }
}
