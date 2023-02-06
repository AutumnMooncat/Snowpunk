package Snowpunk.cutContent;

import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.ChristmasCookiePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChristmasCookies extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChristmasCookies.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1, UP_COST = 0, MAGIC = 5, UP_MAGIC = 3;

    public ChristmasCookies() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        secondMagic = baseSecondMagic = MAGIC;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HolidayCheerUpAction(magicNumber));
        Wiz.applyToSelf(new ChristmasCookiePower(p, secondMagic));
        //Wiz.atb(new HealAction(p, p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(() -> upgradeSecondMagic(UP_MAGIC));
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
    }
}