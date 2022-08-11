package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.FlingScrapMod;
import Snowpunk.cardmods.cores.GainSparePartsMod;
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
public class FlingScrap extends AbstractCoreCard {
    public static final String ID = makeID(FlingScrap.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final EffectTag VALUE = EffectTag.DAMAGE;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    public static final int UP_DAMAGE = 3;
    private static final int PARTS = 2;
    private static final int UP_PARTS = 1;

    public FlingScrap() {
        super(ID, COST, TYPE, EffectTag.DAMAGE, EffectTag.MAGIC);
        baseDamage = damage = secondDamage = baseSecondDamage = DAMAGE;
        baseMagicNumber = magicNumber = baseSecondMagic = secondMagic = PARTS;
    }

    @Override
    public void apply(AbstractCard card) {
        String[] blocks = rawDescription.split(" NL ");
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new FlingScrapMod(blocks[0], EffectTag.DAMAGE, DAMAGE, UP_DAMAGE, useSecondDamage));
        CardModifierManager.addModifier(card, new GainSparePartsMod(blocks[1], EffectTag.MAGIC, PARTS, UP_PARTS, useSecondMagic));
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.FLING_SCRAP, useSecondDamage, UP_DAMAGE));
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.FLING_SCRAP2, useSecondMagic, UP_PARTS));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(UP_DAMAGE);
        upgradeSecondDamage(UP_DAMAGE);
    }
}
