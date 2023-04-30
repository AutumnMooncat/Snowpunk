package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.GumdropPower;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class GumdropButtons extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(GumdropButtons.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public GumdropButtons() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new GumdropPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(2));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
    }
}