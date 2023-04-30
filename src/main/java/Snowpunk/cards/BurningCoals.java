package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BurningCoalsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BurningCoals extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BurningCoals.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public BurningCoals() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BurningCoalsPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
    }
}