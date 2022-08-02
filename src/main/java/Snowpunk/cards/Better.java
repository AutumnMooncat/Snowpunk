package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BetterCreationPower;
import Snowpunk.powers.BetterInventionPower;
import Snowpunk.powers.BetterMachinationPower;
import Snowpunk.powers.BrimstonePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Better extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Better.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    private boolean creation = false;
    private boolean machination = false;

    public Better() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        cardsToPreview = new Fireball();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (machination) {
            Wiz.applyToSelf(new BetterMachinationPower(p, magicNumber));
        } else if (creation) {
            Wiz.applyToSelf(new BetterCreationPower(p, magicNumber));
        } else {
            Wiz.applyToSelf(new BetterInventionPower(p, magicNumber));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            creation = true;
            uDesc();
        });
        addUpgradeData(this, () -> {
            machination = true;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        },1);
    }
}