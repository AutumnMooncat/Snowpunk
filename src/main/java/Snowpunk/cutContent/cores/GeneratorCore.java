package Snowpunk.cutContent.cores;

import Snowpunk.cardmods.BetterExhaustMod;
import Snowpunk.cardmods.cores.*;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class GeneratorCore extends AbstractCoreCard {
    public static final String ID = makeID(GeneratorCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final ValueType VALUE = ValueType.MAGIC;

    private static final int BOOST = 2;
    private static final int UP_BOOST = 1;
    private static final int STEAM = 1;
    private static final int UP_STEAM = 1;
    private static final int MADNESS = 1;
    private static final int UP_MADNESS = 1;
    private static final int SNOW = 1;
    private static final int UP_SNOW = 1;

    String nameToAdd;
    int effectIndex;
    int effects = 4;
    boolean useSecondVar;

    public GeneratorCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = BOOST;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(nameToAdd, TYPE, RARITY, TARGET));
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new GainMEnergyMod(rawDescription, VALUE, BOOST, UP_BOOST, useSecondVar));
                break;
            case 1:
                CardModifierManager.addModifier(card, new GainSteamMod(rawDescription, VALUE, STEAM, UP_STEAM, useSecondVar));
                CardModifierManager.addModifier(card, new BetterExhaustMod());
                break;
            case 2:
                CardModifierManager.addModifier(card, new MadnessMod(rawDescription, VALUE, MADNESS, UP_MADNESS, useSecondVar));
                CardModifierManager.addModifier(card, new BetterExhaustMod());
                break;
            case 3:
                CardModifierManager.addModifier(card, new GainSnowMod(rawDescription, VALUE, SNOW, UP_SNOW, useSecondVar));
                break;
            default:
        }
    }

    @Override
    public void prepForSelection(AssembledCard card, ArrayList<AbstractCoreCard> chosenCores) {
        effectIndex = AbstractDungeon.cardRandomRng.random(effects-1); // Inclusive RNG call needs a -1
        nameToAdd = TEXT[effectIndex*2];
        rawDescription = TEXT[effectIndex*2+1];
        initializeDescription();
        if (chosenCores.stream().anyMatch(c -> c.valueType == VALUE)) {
            swapDynvarKey(VALUE);
            useSecondVar = true;
        }
        if (effectIndex == 0) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = BOOST;
        } else if (effectIndex == 1) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = STEAM;
            CardModifierManager.addModifier(this, new BetterExhaustMod());
        } else if (effectIndex == 2) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = MADNESS;
            CardModifierManager.addModifier(this, new BetterExhaustMod());
        } else {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = SNOW;
        }
    }

    @Override
    public void upp() {
        if (effectIndex == 0) {
            upgradeMagicNumber(UP_BOOST);
            upgradeSecondMagic(UP_BOOST);
        } else if (effectIndex == 1) {
            upgradeMagicNumber(UP_STEAM);
            upgradeSecondMagic(UP_STEAM);
        } else if (effectIndex == 2) {
            upgradeMagicNumber(UP_MADNESS);
            upgradeSecondMagic(UP_MADNESS);
        } else {
            upgradeMagicNumber(UP_SNOW);
            upgradeSecondMagic(UP_SNOW);
        }
    }
}
