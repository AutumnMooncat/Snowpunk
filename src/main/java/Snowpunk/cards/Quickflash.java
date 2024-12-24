package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Quickflash extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Quickflash.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, BLOCK = 4, UP_BLOCK = 3;

    public Quickflash() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT * 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeBlock(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeBlock(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeBlock(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeBlock(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeBlock(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeBlock(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
        setDependencies(true, 3, 2);
        setDependencies(true, 4, 3);
        setDependencies(true, 5, 4);
    }
}