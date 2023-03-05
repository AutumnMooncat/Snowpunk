package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.AshesToAshesPower;
import Snowpunk.powers.BrassContraptionPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassContraption extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BrassContraption.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    public BrassContraption() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BrassContraptionPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
        setDependencies(true, 2, 0);
    }
}