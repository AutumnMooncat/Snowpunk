package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.FineTunePower;
import Snowpunk.powers.WrenchPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class FineTuned extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FineTuned.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1, CLANK = 1, UP_CLANK = 1;

    public FineTuned() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = CLANK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FineTunePower(p, magicNumber));
        //Wiz.applyToSelf(new WrenchPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_CLANK));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}