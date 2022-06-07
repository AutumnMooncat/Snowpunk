package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.BlockBuffMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class BrassPlating extends AbstractPartCard {
    public static final String ID = makeID(BrassPlating.class.getSimpleName());

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;

    public BrassPlating() {
        super(ID, TYPE, RARITY);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isSkill.and(hasBlockValue);
    }

    @Override
    public void prepForSelection(AbstractCard card) {
        if (card.cost > 1) {
            baseMagicNumber *= card.cost;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new BlockBuffMod(magicNumber));
    }
}
