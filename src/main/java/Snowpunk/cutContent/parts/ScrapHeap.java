package Snowpunk.cutContent.parts;

import Snowpunk.cardmods.parts.BlockBuffMod;
import Snowpunk.cards.parts.AbstractPartCard;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
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
