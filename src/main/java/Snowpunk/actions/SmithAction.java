package Snowpunk.actions;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.MultiUpgradePatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.HashMap;

public class SmithAction extends AbstractGameAction {
    boolean openedScreen;
    HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
    CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public SmithAction() {
        this.duration = 0.3F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade) {

            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(cardMap.get(c), MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(c));
                cardMap.get(c).upgrade();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(cardMap.get(c).makeStatEquivalentCopy()));
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            for (AbstractCard c : Wiz.adp().hand.getUpgradableCards().group) {
                AbstractCard copy = c.makeStatEquivalentCopy();
                cardMap.put(copy, c);
                g.addToTop(copy);
            }
            AbstractDungeon.gridSelectScreen.open(g, 1, CardCrawlGame.languagePack.getUIString("CampfireSmithEffect").TEXT[0], true, false, false, false);// 69 70
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }
}
