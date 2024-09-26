package Snowpunk.actions;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class BoostInfoAction extends AbstractGameAction {

    AbstractCard cardToFind;

    public BoostInfoAction(AbstractCard card, int num) {
        cardToFind = card;
        amount = num;
    }

    @Override
    public void update() {
        UUID uuid = cardToFind.uuid;
        for (AbstractCard card : GetAllInBattleInstances.get(uuid)) {
            BoostInfo(card);
        }
        isDone = true;
    }

    void BoostInfo(AbstractCard card) {
        if (card instanceof AbstractEasyCard)
            ((AbstractEasyCard) card).info += amount;
        card.superFlash();
        card.applyPowers();
    }
}
