package Snowpunk.cards.cores;

import Snowpunk.cardmods.BetterExhaustMod;
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
public class AbyssalCore extends AbstractCoreCard {
    public static final String ID = makeID(AbyssalCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final ValueType VALUE = ValueType.MAGIC;

    private static final int VULN = 2;
    private static final int UP_VULN = 1;
    private static final int WEAK = 2;
    private static final int UP_WEAK = 1;
    private static final int SOOT = 3;
    private static final int UP_SOOT = 2;
    private static final int STR_DOWN = 6;
    private static final int UP_STR_DOWN = 2;

    String nameToAdd;
    int effectIndex;
    int effects = 4;
    boolean useSecondVar;

    public AbyssalCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = VULN;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(nameToAdd, TYPE, RARITY, TARGET));
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new ApplyAOEVulnMod(rawDescription, VALUE, VULN, UP_VULN, useSecondVar));
                break;
            case 1:
                CardModifierManager.addModifier(card, new ApplyAOEWeakMod(rawDescription, VALUE, WEAK, UP_WEAK, useSecondVar));
                break;
            case 2:
                CardModifierManager.addModifier(card, new ApplyAOESootMod(rawDescription, VALUE, SOOT, UP_SOOT, useSecondVar));
                break;
            case 3:
                CardModifierManager.addModifier(card, new ApplyAOETempStrDownMod(rawDescription, VALUE, STR_DOWN, UP_STR_DOWN, useSecondVar));
                CardModifierManager.addModifier(card, new BetterExhaustMod());
                break;
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
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = VULN;
        } else if (effectIndex == 1) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = WEAK;
        } else if (effectIndex == 2) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = SOOT;
        } else {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = STR_DOWN;
            CardModifierManager.addModifier(card, new BetterExhaustMod());
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
