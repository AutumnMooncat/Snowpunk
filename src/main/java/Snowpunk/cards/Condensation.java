package Snowpunk.cards;

import Snowpunk.actions.CondenseAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
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

    private static final int COST = 2, UP_COST = 1, EFFECT = 3, UP_EFFECT = 1;
    private boolean condense = false;

    public Condensation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        condense = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new CondensationPower(p, magicNumber));
        if (condense)
            Wiz.atb(new CondenseAction());
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_EFFECT));
        addUpgradeData(() -> upgrade2());
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
    }

    private void upgrade2() {
        condense = true;
        uDesc();
    }
}