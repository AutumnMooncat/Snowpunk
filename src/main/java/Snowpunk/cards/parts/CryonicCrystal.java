package Snowpunk.cards.parts;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class CryonicCrystal extends AbstractPartCard {
    public static final String ID = makeID(CryonicCrystal.class.getSimpleName());

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;

    public CryonicCrystal() {
        super(ID, TYPE, RARITY);
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable;
    }

    @Override
    public void apply(AbstractCard card) {
        CardTemperatureFields.addHeat(card, -2);
    }
}
