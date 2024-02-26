package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatBlast extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(HeatBlast.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, SINGE = 4, UP_SINGE = 2;

    public HeatBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SINGE;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new SingePower(m, p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}