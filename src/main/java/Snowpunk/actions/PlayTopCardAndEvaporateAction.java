package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayTopCardAndEvaporateAction extends AbstractGameAction {

    public PlayTopCardAndEvaporateAction(AbstractCreature target) {
        this.duration = Settings.ACTION_DUR_FAST;// 16
        this.actionType = ActionType.WAIT;// 17
        this.source = AbstractDungeon.player;// 18
        this.target = target;// 19
    }// 21

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 25
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {// 26
                this.isDone = true;// 27
                return;// 28
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {// 31
                this.addToTop(new PlayTopCardAndEvaporateAction(target));// 32
                this.addToTop(new EmptyDeckShuffleAction());// 33
                this.isDone = true;// 34
                return;// 35
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {// 38
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();// 39
                CardTemperatureFields.addHeat(card, CardTemperatureFields.HOT);
                AbstractDungeon.player.drawPile.group.remove(card);// 40
                AbstractDungeon.getCurrRoom().souls.remove(card);// 41
                AbstractDungeon.player.limbo.group.add(card);// 43
                card.current_y = -200.0F * Settings.scale;// 44
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;// 45
                card.target_y = (float) Settings.HEIGHT / 2.0F;// 46
                card.targetAngle = 0.0F;// 47
                card.lighten(false);// 48
                card.drawScale = 0.12F;// 49
                card.targetDrawScale = 0.75F;// 50
                card.applyPowers();// 52
                this.addToTop(new NewQueueCardAction(card, this.target, false, true));// 53
                this.addToTop(new UnlimboAction(card));// 54
                if (!Settings.FAST_MODE) {// 55
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));// 56
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));// 58
                }
            }

            this.isDone = true;// 61
        }

    }// 63
}
