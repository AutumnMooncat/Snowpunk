package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SteamTrainPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamTrain extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SteamTrain.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 5, DAMAGE_INFO = 1, BLOCK_INFO = 2;

    public SteamTrain() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockMult = 2;
        if (info == BLOCK_INFO)
            blockMult = 3;
        int damageMult = 2;
        if (info == DAMAGE_INFO)
            damageMult = 3;
        Wiz.applyToSelf(new SteamTrainPower(p, magicNumber, blockMult, damageMult));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            info = baseInfo = 0;
            upgradeInfo(1);
        });
        addUpgradeData(() -> {
            info = baseInfo = 0;
            upgradeInfo(2);
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 0);
        setExclusions(1, 2);
    }
}