package Snowpunk.cutContent.parts;

import Snowpunk.cardmods.parts.DamageBuffMod;
import Snowpunk.cards.parts.AbstractPartCard;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
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
