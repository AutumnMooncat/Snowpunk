package Snowpunk.cards;

import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.CarolingPower;
import Snowpunk.powers.ChristmasCookiePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.MachineLearning;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Caroling extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Caroling.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2, UP_COST = 1, MAGIC = 3, UP_MAGIC = 2;

    public Caroling() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new CarolingPower(p, secondMagic));
        Wiz.applyToSelf(new DrawPower(p, magicNumber));
        //Wiz.atb(new HealAction(p, p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeSecondMagic(UP_MAGIC));
        addUpgradeData(this, () -> upgradeMagicNumber(1), new int[]{}, new int[]{2});
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST), new int[]{}, new int[]{1});
    }
}