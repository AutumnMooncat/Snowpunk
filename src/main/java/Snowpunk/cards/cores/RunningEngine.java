package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.RunningEngineMod;
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
public class RunningEngine extends AbstractCoreCard {
    public static final String ID = makeID(RunningEngine.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final EffectTag VALUE = EffectTag.MAGIC;

    private static final int COST = 0;
    private static final int EFFECT = 2;
    public static final int UP_EFFECT = 1;

    public RunningEngine() {
        super(ID, COST, TYPE, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = EFFECT;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new RunningEngineMod(rawDescription, useSecondMagic));
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.RUNNING_ENGINE, useSecondMagic, UP_EFFECT));
            ((AssembledCard) card).saveMagic(EFFECT, useSecondMagic);
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        upgradeSecondMagic(UP_EFFECT);
    }
}
