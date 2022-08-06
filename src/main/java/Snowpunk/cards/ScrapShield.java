package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.NextTurnPowerPower;
import Snowpunk.powers.ScrapShieldPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ScrapShield extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ScrapShield.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLK = 12, UP_BLK = 4, UP_BLK_2 = 6, MAGIC = 1, UP_MAGIC = 1;

    public ScrapShield() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new ScrapShieldPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1), new int[]{}, new int[]{1});
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1), new int[]{}, new int[]{0});
        addUpgradeData(this, () -> upgradeBlock(UP_BLK), false, 0, 1);
        addUpgradeData(this, () -> {
            upgradeBlock(UP_BLK_2);
        }, 0, 2);
        addUpgradeData(this, () -> {
            upgradeMagicNumber(MAGIC);
        }, 1, 2);
    }
}