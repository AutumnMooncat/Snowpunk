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
public class CombustionCore extends AbstractCoreCard {
    public static final String ID = makeID(CombustionCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final ValueType VALUE = ValueType.DAMAGE;

    private static final int TRICE_DMG = 5;
    private static final int UP_THRICE_DMG = 2;
    private static final int AOE_DAMAGE = 18;
    private static final int UP_AOE_DMG = 4;
    private static final int SINGLE_DMG = 24;
    private static final int UP_SINGLE_DMG = 6;
    private static final int TWICE_DAMAGE = 8;
    private static final int UP_TWICE_DMG = 3;

    String nameToAdd;
    int effectIndex;
    int effects = 4;

    public CombustionCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseDamage = damage = baseSecondDamage = secondDamage = TRICE_DMG;
    }

    @Override
    public void apply(AbstractCard card) {
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new DealDamageThriceMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, TRICE_DMG, UP_THRICE_DMG));
                break;
            case 1:
                CardModifierManager.addModifier(card, new DealAOEDamageMod(nameToAdd, rawDescription, TYPE, RARITY, CardTarget.ALL_ENEMY, AOE_DAMAGE, UP_AOE_DMG));
                break;
            case 2:
                CardModifierManager.addModifier(card, new DealDamageMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, SINGLE_DMG, UP_SINGLE_DMG));
                break;
            case 3:
                CardModifierManager.addModifier(card, new DealDamageTwiceMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, TWICE_DAMAGE, UP_TWICE_DMG));
                break;
            default:
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
            baseDamage = damage = baseSecondDamage = secondDamage = TRICE_DMG;
        } else if (effectIndex == 1) {
            baseDamage = damage = baseSecondDamage = secondDamage = AOE_DAMAGE;
        } else if (effectIndex == 2) {
            baseDamage = damage = baseSecondDamage = secondDamage = SINGLE_DMG;
        } else {
            baseDamage = damage = baseSecondDamage = secondDamage = TWICE_DAMAGE;
        }
    }

    @Override
    public void upp() {
        if (effectIndex == 0) {
            upgradeDamage(UP_THRICE_DMG);
            upgradeSecondDamage(UP_THRICE_DMG);
        } else if (effectIndex == 1) {
            upgradeDamage(UP_AOE_DMG);
            upgradeSecondDamage(UP_AOE_DMG);
        } else if (effectIndex == 2) {
            upgradeDamage(UP_SINGLE_DMG);
            upgradeSecondDamage(UP_SINGLE_DMG);
        } else {
            upgradeDamage(UP_TWICE_DMG);
            upgradeSecondDamage(UP_TWICE_DMG);
        }
    }
}