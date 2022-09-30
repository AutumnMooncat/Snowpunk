package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.CarolingDrawPower;
import Snowpunk.powers.CarolingSnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Caroling extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Caroling.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    public Caroling() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 1;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new CarolingDrawPower(p, magicNumber));
        Wiz.applyToSelf(new CarolingSnowballPower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(1));
        addUpgradeData(this, () -> upgradeMagicNumber(1));
        addUpgradeData(this, () -> upgradeSecondMagic(1));
    }

    private void upgrade3() {
        upgradeSecondMagic(1);
        uDesc();
    }

    private void upgrade1() {
        upgradeInfo(1);
        isInnate = true;
    }
}