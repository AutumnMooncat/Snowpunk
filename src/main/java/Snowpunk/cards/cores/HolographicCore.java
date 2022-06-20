package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.*;
import Snowpunk.cardmods.cores.edits.CardEditMod;
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
public class HolographicCore extends AbstractCoreCard {
    public static final String ID = makeID(HolographicCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final ValueType VALUE = ValueType.MAGIC;

    private static final int INTAN = 1;
    private static final int UP_INTAN = 1;
    private static final int BUFFER = 1;
    private static final int UP_BUFFER = 1;
    private static final int AFTER = 1;
    private static final int UP_AFTER = 1;
    private static final int METAL = 6;
    private static final int UP_METAL = 3;

    String nameToAdd;
    int effectIndex;
    int effects = 4;
    boolean secondVar;

    public HolographicCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = INTAN;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(nameToAdd, TYPE, RARITY, TARGET));
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new IntangibleMod(rawDescription, VALUE, INTAN, UP_INTAN, secondVar));
                break;
            case 1:
                CardModifierManager.addModifier(card, new BufferMod(rawDescription, VALUE, BUFFER, UP_BUFFER, secondVar));
                break;
            case 2:
                CardModifierManager.addModifier(card, new AfterImageMod(rawDescription, VALUE, AFTER, UP_AFTER, secondVar));
                break;
            case 3:
                CardModifierManager.addModifier(card, new MetallicizeMod(rawDescription, VALUE, METAL, UP_METAL, secondVar));
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
            secondVar = true;
        }
        if (effectIndex == 0) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = INTAN;
        } else if (effectIndex == 1) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = BUFFER;
        } else if (effectIndex == 2) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = AFTER;
        } else {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = METAL;
        }
    }

    @Override
    public void upp() {
        if (effectIndex == 0) {
            upgradeMagicNumber(UP_INTAN);
            upgradeSecondMagic(UP_INTAN);
        } else if (effectIndex == 1) {
            upgradeMagicNumber(UP_BUFFER);
            upgradeSecondMagic(UP_BUFFER);
        } else if (effectIndex == 2) {
            upgradeMagicNumber(UP_AFTER);
            upgradeSecondMagic(UP_AFTER);
        } else {
            upgradeMagicNumber(UP_METAL);
            upgradeSecondMagic(UP_METAL);
        }
    }
}
