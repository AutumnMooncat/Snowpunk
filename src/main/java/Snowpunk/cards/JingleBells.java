package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class JingleBells extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(JingleBells.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 7, UP_BLOCK = 3;

    public JingleBells() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 0;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        blck();
        int brass = getSnow() + magicNumber;
        if (brass <= 0)
            return;
        Wiz.applyToSelf(new BrassPower(player, brass));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> CardTemperatureFields.addHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeMagicNumber(2));
    }
}