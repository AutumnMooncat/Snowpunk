package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrimstonePower;
import Snowpunk.powers.CondensationPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Condensation extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Condensation.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;
    private static final int UP_EFFECT_2 = 2;

    public Condensation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        cardsToPreview = new Fireball();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new CondensationPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_EFFECT));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_EFFECT_2), 1);
    }
}