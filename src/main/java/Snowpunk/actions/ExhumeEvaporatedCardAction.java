package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.CondenseEffect;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class ExhumeEvaporatedCardAction extends AbstractGameAction {
    int numHats, bonusHot;
    boolean random;
    AbstractCard cardToIgnore;

    public ExhumeEvaporatedCardAction(int number, int numHats, boolean random) {
        amount = number;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.numHats = numHats;
        this.random = random;
        cardToIgnore = null;
    }

    public ExhumeEvaporatedCardAction(int number, int numHats, boolean random, AbstractCard card) {
        amount = number;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.numHats = numHats;
        this.random = random;
        this.cardToIgnore = card;
    }

    @Override
    public void update() {
        if (random) {
            ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
            for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
                if (cardToIgnore == null || card != cardToIgnore)
                    selectionGroup.add(card);
            }
            if (amount >= EvaporatePanel.evaporatePile.group.size()) {
                for (AbstractCard card : selectionGroup)
                    ExhumeCard(card);
            } else {
                for (int i = 0; i < amount; i++) {
                    int rand = AbstractDungeon.cardRandomRng.random(selectionGroup.size() - 1);
                    ExhumeCard(selectionGroup.get(rand));
                    selectionGroup.remove(rand);
                }
            }
            isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (amount >= EvaporatePanel.evaporatePile.group.size()) {
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
                    if (cardToIgnore == null || card != cardToIgnore)
                        selectionGroup.add(card);
                }
                for (AbstractCard card : selectionGroup) {
                    ExhumeCard(card);
                }
            } else {
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();

                for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
                    if (cardToIgnore == null || c != cardToIgnore) {
                        AbstractCard copy = c.makeStatEquivalentCopy();
                        cardMap.put(copy, c);
                        selectionGroup.add(copy);
                    }
                }

                Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, amount, "", false, card -> true, cards -> {
                    for (AbstractCard c : cards) {
                        ExhumeCard(cardMap.get(c));
                    }
                }));
            }
        }
        tickDuration();
    }

    private void ExhumeCard(AbstractCard cardToExhume) {
        cardToExhume.unhover();
        cardToExhume.unfadeOut();
        cardToExhume.lighten(true);
        cardToExhume.fadingOut = false;
        if (numHats > 0)
            CardModifierManager.addModifier(cardToExhume, new HatMod(numHats));
        if (bonusHot > 0)
            CardTemperatureFields.addHeat(cardToExhume, bonusHot);
        AbstractDungeon.topLevelEffects.add(new CondenseEffect(cardToExhume, true));
    }
}
