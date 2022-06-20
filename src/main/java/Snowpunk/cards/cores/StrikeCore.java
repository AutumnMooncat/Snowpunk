package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.DealAOEDamageMod;
import Snowpunk.cardmods.cores.DealDamageMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class StrikeCore extends AbstractCoreCard {
    public static final String ID = makeID(StrikeCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final ValueType VALUE = ValueType.DAMAGE;

    private static final int DAMAGE = 9;
    private static final int UP_DAMAGE = 3;

    boolean useSecondVar;

    public StrikeCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseDamage = damage = secondDamage = baseSecondDamage = DAMAGE;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], TYPE, RARITY, TARGET));
        CardModifierManager.addModifier(card, new DealDamageMod(rawDescription, VALUE, DAMAGE, UP_DAMAGE, useSecondVar));
    }

    @Override
    public void prepForSelection(AssembledCard card, ArrayList<AbstractCoreCard> chosenCores) {
        if (chosenCores.stream().anyMatch(c -> c.valueType == VALUE)) {
            swapDynvarKey(VALUE);
            useSecondVar = true;
        }
    }

    @Override
    public void upp() {
        upgradeDamage(UP_DAMAGE);
        upgradeSecondDamage(UP_DAMAGE);
    }
}
