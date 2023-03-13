package Snowpunk.actions;

import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static Snowpunk.util.Wiz.adp;
import static Snowpunk.util.Wiz.getRandomItem;

public class GiftDiscoveryAction extends AbstractGameAction {
    boolean freeThisTurn;
    private boolean retrieveCard = false;

    public GiftDiscoveryAction(int amount, boolean free) {
        this.amount = amount;
        freeThisTurn = free;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if (freeThisTurn)
                    disCard.setCostForTurn(0);
                disCard.current_x = -1000.0F * Settings.xScale;
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard.CardRarity> rarityList = new ArrayList<>();
        rarityList.add(AbstractCard.CardRarity.COMMON);
        rarityList.add(AbstractCard.CardRarity.UNCOMMON);
        rarityList.add(AbstractCard.CardRarity.RARE);
        ArrayList<AbstractCard> derp = new ArrayList<>();
        while (derp.size() != this.amount) {
            boolean dupe = false;
            boolean heal = false;
            AbstractCard tmp = CardLibrary.getAnyColorCard(getRandomItem(rarityList));
            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
                if (tmp.hasTag(AbstractCard.CardTags.HEALING))
                    heal = true;
            }
            if (!dupe && !heal)
                derp.add(tmp.makeCopy());
        }
        return derp;
    }
}
