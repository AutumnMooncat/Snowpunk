package Snowpunk.cards.parts;

import Snowpunk.cardmods.DamageBuffMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class BrokenSprockets extends AbstractPartCard {
    public static final String ID = makeID(BrokenSprockets.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;

    public BrokenSprockets() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isAttack.and(doesntExhaust);
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new DamageBuffMod(6));
        CardModifierManager.addModifier(card, new ExhaustMod());
    }
}
