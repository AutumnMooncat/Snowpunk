package Snowpunk.actions;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.util.Wiz.adp;

public class GainWaterAction extends AbstractGameAction {

    AbstractCard playedCard;

    public GainWaterAction(AbstractCard card, int amount) {
        playedCard = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (CardTemperatureFields.getCardHeat(playedCard) == 0)
            Wiz.att(new GainEnergyAction(amount));

        if (CardTemperatureFields.getCardHeat(playedCard) < 0)
            Wiz.applyToSelf(new SnowballPower(Wiz.adp(), amount));

        if (CardTemperatureFields.getCardHeat(playedCard) > 0)
            Wiz.applyToSelf(new SteamPower(Wiz.adp(), amount));

        isDone = true;
    }
}
