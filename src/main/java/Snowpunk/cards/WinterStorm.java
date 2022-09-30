package Snowpunk.cards;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.WinterStormPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class WinterStorm extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(WinterStorm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int STACKS = 1;

    private boolean coolEngine = false;

    public WinterStorm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new WinterStormPower(p, magicNumber));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(0));
        addUpgradeData(this, () -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
    }
}