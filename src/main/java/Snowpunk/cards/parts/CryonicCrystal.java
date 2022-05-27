package Snowpunk.cards.parts;

import Snowpunk.cardmods.TemperatureMod;
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
        CardModifierManager.addModifier(this, new TemperatureMod(false, -2));
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new TemperatureMod(false, -2));
    }
}
