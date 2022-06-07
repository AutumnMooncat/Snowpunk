package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.CoolOnDrawMod;
import Snowpunk.cardmods.parts.GainEnergyMod;
import Snowpunk.cardmods.parts.HeatOnDrawMod;
import Snowpunk.cardmods.parts.ReshuffleMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Gyroscopter extends AbstractPartCard {
    public static final String ID = makeID(Gyroscopter.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    public Gyroscopter() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(c -> !c.shuffleBackIntoDrawPile);
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new ReshuffleMod());
    }
}
