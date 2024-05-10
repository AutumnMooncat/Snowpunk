package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.AllAboardPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class AllAboard extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(AllAboard.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private boolean makeEthereal, upgrade;

    public AllAboard() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
        info = baseInfo = 0;
        makeEthereal = false;
        upgrade = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AllAboardPower(p, magicNumber, makeEthereal, upgrade));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            makeEthereal = true;
            uDesc();
        });
        addUpgradeData(() -> {
            upgrade = true;
            upgradeInfo(1);
        });
    }
}