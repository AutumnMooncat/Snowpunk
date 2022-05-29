package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.DamageBuffMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class SpareSpikes extends AbstractPartCard {
    public static final String ID = makeID(SpareSpikes.class.getSimpleName());

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;

    public SpareSpikes() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isAttack.and(hasDamageValue);
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new DamageBuffMod(3));
    }
}
