package Snowpunk.actions;

import Snowpunk.TheConductor;
import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
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
    private boolean retrieveCard = false, otherColors = true;

    public GiftDiscoveryAction(int amount, boolean free, boolean otherColors) {
        this.amount = amount;
        freeThisTurn = free;
        duration = Settings.ACTION_DUR_FAST;
        this.otherColors = otherColors;
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
                AbstractCard chosenCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if (freeThisTurn)
                    CardModifierManager.addModifier(chosenCard, new HatMod());
                chosenCard.current_x = -1000.0F * Settings.xScale;
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(chosenCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(chosenCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
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
        while (derp.size() < this.amount) {
            AbstractCard tmp = CardLibrary.getAnyColorCard(getRandomItem(rarityList));
            boolean dupe = false;
            boolean heal = false;
            boolean poolsComp = false;
            if (otherColors && tmp.color == TheConductor.Enums.SNOWY_BLUE_COLOR)
                continue;
            if (!otherColors)
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (dupe)
                continue;
            if (tmp.hasTag(AbstractCard.CardTags.HEALING)) {
                //   heal = true;
                continue;
            }
            if (tmp.getClass().isAnnotationPresent(NoPools.class) || tmp.getClass().isAnnotationPresent(NoCompendium.class)) {
                //    poolsComp = true;
                continue;
            }
            //if (!dupe && !heal && !poolsComp && !(tmp instanceof HandOfGreed))
            derp.add(tmp.makeCopy());
        }
        return derp;
    }
}
