package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.CoolOnDrawMod;
import Snowpunk.cardmods.parts.HeatOnDrawMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class GlowingMetal extends AbstractPartCard {
    public static final String ID = makeID(GlowingMetal.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    public GlowingMetal() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(c -> !CardModifierManager.hasModifier(c, HeatOnDrawMod.ID) && !CardModifierManager.hasModifier(c, CoolOnDrawMod.ID));
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new HeatOnDrawMod());
    }
}
