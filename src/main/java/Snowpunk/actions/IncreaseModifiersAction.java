package Snowpunk.actions;

import Snowpunk.cardmods.*;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class IncreaseModifiersAction extends AbstractGameAction {

    public static final String ID = makeID("DoubleModifiers");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    boolean random, x2;
    ArrayList<AbstractCard> modCards, nonModCards;

    public IncreaseModifiersAction(boolean random, int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;

        modCards = new ArrayList<>();
        nonModCards = new ArrayList<>();
        this.random = random;
        x2 = amount < 0;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (CardModifierManager.hasModifier(c, GearMod.ID) || CardModifierManager.hasModifier(c, PlateMod.ID) ||
                        CardModifierManager.hasModifier(c, TemperatureMod.ID) || CardModifierManager.hasModifier(c, HatMod.ID) ||
                        CardModifierManager.hasModifier(c, OverdriveMod.ID) || CardModifierManager.hasModifier(c, ChillMod.ID))
                    modCards.add(c);
                else
                    nonModCards.add(c);
            }

            Wiz.adp().hand.group.removeAll(nonModCards);
            if (Wiz.adp().hand.size() == 0) {
                isDone = true;
                returnCards();
                return;
            }

            if (Wiz.adp().hand.size() <= 1) {
                isDone = true;
                for (AbstractCard c : Wiz.adp().hand.group)
                    increaseMods(c);
                returnCards();
                return;
            }
            if (random) {
                isDone = true;
                AbstractCard card = Wiz.adp().hand.getRandomCard(true);
                increaseMods(card);
                returnCards();
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                increaseMods(c);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            returnCards();
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : nonModCards)
            Wiz.adp().hand.addToTop(c);

        Wiz.adp().hand.refreshHandLayout();
    }

    private void increaseMods(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, GearMod.ID))
            CardModifierManager.addModifier(card, new GearMod(x2 ? ((GearMod) CardModifierManager.getModifiers(card, GearMod.ID).get(0)).amount : amount));
        if (CardModifierManager.hasModifier(card, PlateMod.ID))
            CardModifierManager.addModifier(card, new PlateMod(x2 ? ((PlateMod) CardModifierManager.getModifiers(card, PlateMod.ID).get(0)).amount : amount));
        if (CardModifierManager.hasModifier(card, HatMod.ID))
            CardModifierManager.addModifier(card, new HatMod(x2 ? ((HatMod) CardModifierManager.getModifiers(card, HatMod.ID).get(0)).amount : amount));
        if (CardModifierManager.hasModifier(card, TemperatureMod.ID)) {
            if (x2)
                CardTemperatureFields.addHeat(card, CardTemperatureFields.getCardHeat(card));
            else
                CardTemperatureFields.addHeat(card, amount * CardTemperatureFields.getCardHeat(card) / Math.abs(CardTemperatureFields.getCardHeat(card)));
        }
        if (CardModifierManager.hasModifier(card, OverdriveMod.ID))
            CardModifierManager.addModifier(card, new OverdriveMod(x2 ? ((OverdriveMod) CardModifierManager.getModifiers(card, OverdriveMod.ID).get(0)).amount : amount));
        if (CardModifierManager.hasModifier(card, ChillMod.ID))
            CardModifierManager.addModifier(card, new ChillMod(x2 ? ((ChillMod) CardModifierManager.getModifiers(card, ChillMod.ID).get(0)).amount : amount));
        card.superFlash();
        card.applyPowers();
    }
}
