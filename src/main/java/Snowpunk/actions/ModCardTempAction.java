package Snowpunk.actions;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModCardTempAction extends AbstractGameAction {
    AbstractCard card;
    boolean random;

    public ModCardTempAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public ModCardTempAction(boolean random, int amount) {
        this.random = random;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (card != null) {
            CardTemperatureFields.addHeat(card, amount);
        } else {
            if (random) {
                CardTemperatureFields.addHeat(AbstractDungeon.player.hand.getRandomCard(true), amount);
            } else {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    CardTemperatureFields.addHeat(c, amount);
                }
            }
        }
        this.isDone = true;
    }
}
