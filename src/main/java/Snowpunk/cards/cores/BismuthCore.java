package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.*;
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
public class BismuthCore extends AbstractCoreCard {
    public static final String ID = makeID(BismuthCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final ValueType VALUE = ValueType.MAGIC;

    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    String nameToAdd;
    int effectIndex;
    int effects = 4;

    public BismuthCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = EFFECT;
    }

    @Override
    public void apply(AbstractCard card) {
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new ApplyVulnMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, EFFECT, UP_EFFECT));
                break;
            case 1:
                CardModifierManager.addModifier(card, new ApplyWeakMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, EFFECT, UP_EFFECT));
                break;
            case 2:
                CardModifierManager.addModifier(card, new ApplySootMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, EFFECT+1, UP_EFFECT));
                break;
            case 3:
                CardModifierManager.addModifier(card, new ApplyTempStrDownMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, EFFECT+1, UP_EFFECT+1));
                break;
            default:
                throw new IllegalStateException("[Bismuth Core] - Unexpected value: " + effectIndex);
        }
    }

    @Override
    public void prepForSelection(ArrayList<AbstractCoreCard> chosenCores) {
        effectIndex = AbstractDungeon.cardRandomRng.random(effects-1); // Inclusive RNG call needs a -1
        nameToAdd = TEXT[effectIndex*2];
        rawDescription = TEXT[effectIndex*2+1];
        initializeDescription();
        if (chosenCores.stream().anyMatch(c -> c.valueType == VALUE)) {
            swapDynvarKey(VALUE);
        }
        if (effectIndex > 1) {
            baseMagicNumber += 1;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        upgradeSecondMagic(UP_EFFECT);
    }
}
