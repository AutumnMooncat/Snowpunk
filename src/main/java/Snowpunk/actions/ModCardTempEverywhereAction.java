package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModCardTempEverywhereAction extends AbstractGameAction {

    public ModCardTempEverywhereAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            CardTemperatureFields.addHeat(c, amount);
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            CardTemperatureFields.addHeat(c, amount);
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            CardTemperatureFields.addHeat(c, amount);
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            CardTemperatureFields.addHeat(c, amount);
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            CardTemperatureFields.addHeat(c, amount);
        }
        for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
            CardTemperatureFields.addHeat(c, amount);
        }
        isDone = true;
    }
}
