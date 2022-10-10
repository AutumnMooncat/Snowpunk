package Snowpunk.cards;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrimstonePower;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SteamFormPower;
import Snowpunk.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class FireAndBrimstone extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FireAndBrimstone.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2, FIREBALLS = 1, BRIMSTONE = 3, UP_FIRE = 1, UP_BRIM = 2;

    private boolean heatEngine = false;

    public FireAndBrimstone() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = FIREBALLS;
        baseSecondMagic = secondMagic = BRIMSTONE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FireballPower(p, magicNumber));
        Wiz.applyToSelf(new BrimstonePower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(UP_FIRE));
        addUpgradeData(() -> upgradeSecondMagic(UP_BRIM));
    }
}