package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.JumblomaticPower;
import Snowpunk.powers.ReverseNextClankPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Jumblomatic extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Jumblomatic.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1, CLANK = 1;

    public Jumblomatic() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = CLANK;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (info > 0)
            Wiz.applyToSelf(new ReverseNextClankPower(p, 1));
        Wiz.applyToSelf(new JumblomaticPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeInfo(1));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
    }
}