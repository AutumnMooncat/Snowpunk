package Snowpunk.cards.parts;

import Snowpunk.cardmods.TemperatureMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatingCoils extends AbstractPartCard {
    public static final String ID = makeID(HeatingCoils.class.getSimpleName());

    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    public HeatingCoils() {
        super(ID, TYPE);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new TemperatureMod(false, 1));
    }

    @Override
    public void upp() {
        //If we want to upgrade parts we can support it, we dont need to if we dont want though
    }
}
