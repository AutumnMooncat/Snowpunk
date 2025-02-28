package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.JumblomaticPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Jumblomatic extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Jumblomatic.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1, CLANK = 1, UP_CLANK = 1;

    public Jumblomatic() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = CLANK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new JumblomaticPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
    }
}