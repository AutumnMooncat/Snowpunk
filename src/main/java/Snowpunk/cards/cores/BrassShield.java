package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.GainBlockMod;
import Snowpunk.cardmods.cores.ScavengeStrikeMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class BrassShield extends AbstractCoreCard {
    public static final String ID = makeID(BrassShield.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final ValueType VALUE = ValueType.BLOCK;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UP_BLOCK = 3;

    public BrassShield() {
        super(ID, COST, TYPE, VALUE);
        baseBlock = block = secondBlock = baseSecondBlock = BLOCK;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new GainBlockMod(rawDescription, VALUE, BLOCK, UP_BLOCK, useSecondVar));
    }

    @Override
    public void upp() {
        upgradeBlock(UP_BLOCK);
        upgradeSecondBlock(UP_BLOCK);
    }
}
