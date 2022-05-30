package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.DealAOEDamageMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class BlastCore extends AbstractCoreCard {
    public static final String ID = makeID(BlastCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UP_DAMAGE = 2;

    public BlastCore() {
        super(ID, COST, TYPE, RARITY);
        baseDamage = damage = DAMAGE;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new DealAOEDamageMod(TEXT[0], rawDescription, COST, TYPE, RARITY, TARGET, DAMAGE, UP_DAMAGE));
    }

    @Override
    public void upp() {
        upgradeDamage(UP_DAMAGE);
    }
}
