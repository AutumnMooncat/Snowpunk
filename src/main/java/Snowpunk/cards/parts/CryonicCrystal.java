package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.CoolOnDrawMod;
import Snowpunk.cardmods.parts.HeatOnDrawMod;
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
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(c -> !CardModifierManager.hasModifier(c, HeatOnDrawMod.ID) && !CardModifierManager.hasModifier(c, CoolOnDrawMod.ID));
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CoolOnDrawMod());
    }
}
