package Snowpunk.cards;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.TheSnowmanPower;
import Snowpunk.powers.WinterStormPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TheSnowman extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TheSnowman.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int STACKS = 1;
    private static final int UP_STACKS = 1;

    public TheSnowman() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new TheSnowmanPower(p));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
    }
}