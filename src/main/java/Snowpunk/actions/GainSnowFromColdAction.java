package Snowpunk.actions;

import Snowpunk.cardmods.ChillMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class GainSnowFromColdAction extends AbstractGameAction {
    public static final String ID = makeID("Cryo");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private final AbstractPlayer p;

    public GainSnowFromColdAction(int bonus) {
        this.actionType = ActionType.CARD_MANIPULATION;
        amount = bonus;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        int numCold = 0;
        for (AbstractCard c : p.hand.group) {
            if (CardTemperatureFields.getCardHeat(c) == CardTemperatureFields.COLD)
                numCold++;
        }

        numCold += amount;
        if (numCold > 0)
            Wiz.att(new GainSnowballAction(numCold));
        isDone = true;
    }
}
