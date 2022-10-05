package Snowpunk.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UpgradeRandomCardsAction extends AbstractGameAction {
    private AbstractPlayer p;

    public UpgradeRandomCardsAction(int num) {
        this.amount = num;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() <= 0) {
                this.isDone = true;
                return;
            }
            CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                if (c.canUpgrade() && c.type != AbstractCard.CardType.STATUS)
                    upgradeable.addToTop(c);
            }
            if (upgradeable.size() > 0) {
                upgradeable.shuffle();
                for (int i = 0; i < amount && i < upgradeable.size(); i++) {
                    (upgradeable.group.get(i)).upgrade();
                    (upgradeable.group.get(i)).superFlash();
                    (upgradeable.group.get(i)).applyPowers();
                }
            }
            this.isDone = true;
            return;
        }
        tickDuration();
    }
}
