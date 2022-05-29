package Snowpunk.cards.parts;

import Snowpunk.patches.CardTemperatureFields;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatingCoils extends AbstractPartCard {
    public static final String ID = makeID(HeatingCoils.class.getSimpleName());

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;

    public HeatingCoils() {
        super(ID, TYPE, RARITY);
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(c -> CardTemperatureFields.getCardHeat(c) != 2);
    }

    @Override
    public void apply(AbstractCard card) {
        CardTemperatureFields.addHeat(card, 1);
    }
}
