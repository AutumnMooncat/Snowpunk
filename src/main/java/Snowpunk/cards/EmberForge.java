package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.EmberForgePower;
import Snowpunk.powers.HeatTransferPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class EmberForge extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(EmberForge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public EmberForge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new EmberForgePower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            this.isInnate = true;
            uDesc();
        });
        addUpgradeData(this, () -> upgradeMagicNumber(UP_EFFECT));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1));
    }
}