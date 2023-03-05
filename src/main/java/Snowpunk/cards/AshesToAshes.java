package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.AshesToAshesPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.WinterStormPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class AshesToAshes extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(AshesToAshes.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int STACKS = 1;

    public AshesToAshes() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AshesToAshesPower(p, magicNumber));
        if (secondMagic > 0)
            Wiz.applyToSelf(new FireballPower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(() -> {
            secondMagic = baseSecondMagic = 0;
            upgradeSecondMagic(1);
        });
    }
}