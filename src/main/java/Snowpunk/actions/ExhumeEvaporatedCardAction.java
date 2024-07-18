package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.CondenseEffect;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class ExhumeEvaporatedCardAction extends AbstractGameAction {
    int numHats, bonusHot;

    public ExhumeEvaporatedCardAction(int number, int numHats) {
        this(number, numHats, 0);
    }

    public ExhumeEvaporatedCardAction(int number, int numHats, int bonusHot) {
        amount = number;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        this.numHats = numHats;
        this.bonusHot = bonusHot;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (amount >= EvaporatePanel.evaporatePile.group.size()) {
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
                    selectionGroup.add(card);
                }
                for (AbstractCard card : selectionGroup) {
                    ExhumeCard(card);
                }
            } else {
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();

                for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
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
