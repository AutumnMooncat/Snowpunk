package Snowpunk.cards.cores;

import Snowpunk.cardmods.FluxMod;
import Snowpunk.cardmods.cores.GainBlockMod;
import Snowpunk.cardmods.cores.MonkeyWrenchMod;
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
public class MonkeyWrench extends AbstractCoreCard {
    public static final String ID = makeID(MonkeyWrench.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int BLOCK = 4;
    public static final int UP_BLOCK = 2;
    private static final int DAMAGE = 4;
    public static final int UP_DAMAGE = 2;

    public MonkeyWrench() {
        super(ID, COST, TYPE, EffectTag.BLOCK, EffectTag.DAMAGE);
        baseDamage = damage = secondDamage = baseSecondDamage = DAMAGE;
        baseBlock = block = baseSecondBlock = secondBlock = BLOCK;
        CardModifierManager.addModifier(this, new FluxMod());
    }

    @Override
    public void apply(AbstractCard card) {
        String[] blocks = rawDescription.split(" NL ");
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new GainBlockMod(blocks[0], EffectTag.BLOCK, BLOCK, UP_BLOCK, useSecondBlock));
        CardModifierManager.addModifier(card, new MonkeyWrenchMod(blocks[1], EffectTag.DAMAGE, DAMAGE, UP_DAMAGE, useSecondDamage));
        CardModifierManager.addModifier(card, new FluxMod());
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.MONKEY_WRENCH, useSecondBlock, useSecondDamage ? 1 : 0));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(UP_DAMAGE);
        upgradeSecondDamage(UP_DAMAGE);
        upgradeBlock(UP_BLOCK);
        upgradeSecondDamage(UP_BLOCK);
    }
}
