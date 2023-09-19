package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FullSteamAheadPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TryEmberForgeCopyAction extends AbstractGameAction {

    AbstractCard card;
    FullSteamAheadPower emberForgePower;
    UseCardAction action;

    public TryEmberForgeCopyAction(AbstractCard card, UseCardAction action, FullSteamAheadPower power) {
        this.card = card;
        this.action = action;
        this.emberForgePower = power;
    }

    @Override
    public void update() {
        if (CardTemperatureFields.getCardHeat(card) == CardTemperatureFields.HOT && !card.purgeOnUse) {
            emberForgePower.flash();
            AbstractMonster monster = null;
            if (action.target != null)
                monster = (AbstractMonster) action.target;
            for (int i = 0; i < emberForgePower.amount; i++) {
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (monster != null)
                    tmp.calculateCardDamage(monster);
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, monster, card.energyOnUse, true, true), true);
            }
        }
        isDone = true;
    }
}
