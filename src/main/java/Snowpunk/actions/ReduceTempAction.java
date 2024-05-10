package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class ReduceTempAction extends AbstractGameAction {
    AbstractCard card;

    public ReduceTempAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        CardTemperatureFields.reduceTemp(card);
        isDone = true;
    }

}
