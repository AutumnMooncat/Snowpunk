package Snowpunk.actions;

import Snowpunk.cardmods.GearMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class BiasedCogAction extends AbstractGameAction {

    public BiasedCogAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> gCards = new ArrayList<>();
        for (AbstractCard card : Wiz.adp().hand.group) {
            if (CardModifierManager.hasModifier(card, GearMod.ID)) {
                GearMod gearMod = (GearMod) CardModifierManager.getModifiers(card, GearMod.ID).get(0);
                gCards.add(card);
            }
        }
        if (gCards.size() == 0) {
            isDone = true;
            return;
        }
        if (gCards.size() <= amount) {
            for (AbstractCard card : gCards)
                Wiz.att(new ApplyCardModifierWithVisualAction(card, new GearMod(1)));
        } else {
            Collections.shuffle(gCards, AbstractDungeon.shuffleRng.random);
            for (int i = 0; i < amount; i++)
                Wiz.att(new ApplyCardModifierWithVisualAction(gCards.get(i), new GearMod(1)));
        }
        isDone = true;
    }
}
