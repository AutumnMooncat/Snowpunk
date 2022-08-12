package Snowpunk.cards.cores;

import Snowpunk.cardmods.MendingMod;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cardmods.cores.GainBlockMod;
import Snowpunk.cardmods.cores.PipeFenceMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import Snowpunk.util.Triplet;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class PipeFence extends AbstractCoreCard {
    public static final String ID = makeID(PipeFence.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final EffectTag VALUE = EffectTag.BLOCK;

    private static final int COST = 2;
    private static final int BLOCK = 4;
    public static final int UP_BLOCK = 1;

    public PipeFence() {
        super(ID, COST, TYPE, VALUE);
        baseBlock = block = secondBlock = baseSecondBlock = BLOCK;
        CardModifierManager.addModifier(this, new VentMod());
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new PipeFenceMod(rawDescription, useSecondBlock));
        CardModifierManager.addModifier(card, new VentMod());
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.PIPE_FENCE, useSecondBlock, UP_BLOCK));
            ((AssembledCard) card).saveBlock(BLOCK, useSecondBlock);
        }
    }

    @Override
    public void upp() {
        upgradeBlock(UP_BLOCK);
        upgradeSecondBlock(UP_BLOCK);
    }
}
