package Snowpunk.cards.parts;

import Snowpunk.cardmods.BlockBuffMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public class ScrapHeap extends AbstractPartCard {
    public static final String ID = makeID(ScrapHeap.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;

    public ScrapHeap() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isSkill.and(hasBlockValue).and(doesntExhaust);
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new BlockBuffMod(6));
        CardModifierManager.addModifier(card, new ExhaustMod());
    }
}
