package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowStack extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SnowStack.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BLOCK = 10;
    private static final int UP_BLOCK = 4;

    public SnowStack() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLOCK;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (magicNumber > 0)
            Wiz.applyToSelf(new SnowballPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(1);
        });
        setDependencies(true, 2, 0, 1);
    }
}