package Snowpunk.actions;

import Snowpunk.cardmods.TemperatureMod;
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
            CardModifierManager.addModifier(card, new TemperatureMod(false, amount));
        } else {
            if (random) {
                CardModifierManager.addModifier(AbstractDungeon.player.hand.getRandomCard(true), new TemperatureMod(false, amount));
            } else {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    CardModifierManager.addModifier(c, new TemperatureMod(false, amount));
                }
            }
        }
        this.isDone = true;
    }
}
