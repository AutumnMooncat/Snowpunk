package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BackupBoilerPower;
import Snowpunk.powers.SteamFormPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BackupBoiler extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BackupBoiler.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int EFFECT = 2;

    public BackupBoiler() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SteamPower(p, magicNumber));
        Wiz.applyToSelf(new BackupBoilerPower(p, -1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(1));
        addUpgradeData(this, () -> upgradeMagicNumber(1), 0);
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
    }
}