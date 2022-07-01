package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.ThornsMod;
import Snowpunk.cardmods.parts.TinkerNextMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Barbs extends AbstractPartCard {
    public static final String ID = makeID(Barbs.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    public Barbs() {
        super(ID, TYPE, RARITY);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(isSkill);
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new ThornsMod(magicNumber));
    }
}
