package Snowpunk.actions;

import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class EvaporateRandomCardAction extends AbstractGameAction {
    private AbstractPlayer p;

    public EvaporateRandomCardAction() {
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (p.hand.group.size() <= 0) {
            isDone = true;
            return;
        }
        AbstractCard card = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        EvaporatePanel.evaporatePile.addToTop(card);
        AbstractDungeon.player.hand.removeCard(card);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(card));
        for (AbstractPower pow : Wiz.adp().powers) {
            if (pow instanceof OnEvaporatePower) {
                ((OnEvaporatePower) pow).onEvaporate(card);
            }
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower pow : m.powers) {
                if (pow instanceof OnEvaporatePower) {
                    ((OnEvaporatePower) pow).onEvaporate(card);
                }
            }
        }
        isDone = true;
    }
}
