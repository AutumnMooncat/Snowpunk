package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.MagicBuffMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ManastoneCasing extends AbstractPartCard {
    public static final String ID = makeID(ManastoneCasing.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;

    public ManastoneCasing() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return c -> c.baseMagicNumber >= 2;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new MagicBuffMod(2));
    }
}
