package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.GainBlockMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class BarrierCore extends AbstractCoreCard {
    public static final String ID = makeID(BarrierCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final ValueType VALUE = ValueType.BLOCK;

    private static final int EFFECT = 6;
    private static final int UP_EFFECT = 3;

    String textToAdd;

    public BarrierCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseBlock = block = EFFECT;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new GainBlockMod(TEXT[0], textToAdd, TYPE, RARITY, TARGET, EFFECT, UP_EFFECT));
    }

    @Override
    public void prepForSelection(ArrayList<AbstractCoreCard> chosenCores) {
        if (chosenCores.stream().anyMatch(c -> c.valueType == VALUE)) {
            textToAdd = String.format(TEXT[1],"!Snowpunk:B2!");
        } else {
            textToAdd = String.format(TEXT[1],"!B!");
        }
    }

    @Override
    public void upp() {
        upgradeBlock(UP_EFFECT);
    }
}
