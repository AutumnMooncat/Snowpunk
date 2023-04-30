package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.UUID;

import static Snowpunk.util.Wiz.adp;

public class PurgeCardAction extends AbstractGameAction {

    UUID uuid;

    public PurgeCardAction(UUID UUID) {
        uuid = UUID;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(uuid)) {
                //AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(c);
                isDone = true;
                return;
            }
        }
        isDone = true;
    }
}
