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
public class AbyssalCore extends AbstractCoreCard {
    public static final String ID = makeID(AbyssalCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final ValueType VALUE = ValueType.MAGIC;

    private static final int VULN = 1;
    private static final int UP_VULN = 1;
    private static final int WEAK = 1;
    private static final int UP_WEAK = 1;
    private static final int SOOT = 2;
    private static final int UP_SOOT = 1;
    private static final int STR_DOWN = 2;
    private static final int UP_STR_DOWN = 2;

    String nameToAdd;
    int effectIndex;
    int effects = 4;

    public AbyssalCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = VULN;
    }

    @Override
    public void apply(AbstractCard card) {
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new ApplyAOEVulnMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, VULN, UP_VULN));
                break;
            case 1:
                CardModifierManager.addModifier(card, new ApplyAOEWeakMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, WEAK, UP_WEAK));
                break;
            case 2:
                CardModifierManager.addModifier(card, new ApplyAOESootMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, SOOT, UP_SOOT));
                break;
            case 3:
                CardModifierManager.addModifier(card, new ApplyAOETempStrDownMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, STR_DOWN, UP_STR_DOWN));
                break;
            default:
                throw new IllegalStateException("[Abyssal Core] - Unexpected value: " + effectIndex);
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
        if (effectIndex == 0) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = VULN;
        } else if (effectIndex == 1) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = WEAK;
        } else if (effectIndex == 2) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = SOOT;
        } else {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = STR_DOWN;
        }
    }

    @Override
    public void upp() {
        if (effectIndex == 0) {
            upgradeMagicNumber(UP_VULN);
            upgradeSecondMagic(UP_VULN);
        } else if (effectIndex == 1) {
            upgradeMagicNumber(UP_WEAK);
            upgradeSecondMagic(UP_WEAK);
        } else if (effectIndex == 2) {
            upgradeMagicNumber(UP_SOOT);
            upgradeSecondMagic(UP_SOOT);
        } else {
            upgradeMagicNumber(UP_STR_DOWN);
            upgradeSecondMagic(UP_STR_DOWN);
        }
    }
}
