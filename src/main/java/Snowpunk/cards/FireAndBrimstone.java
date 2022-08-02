package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrimstonePower;
import Snowpunk.powers.SteamFormPower;
import Snowpunk.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class FireAndBrimstone extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FireAndBrimstone.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public FireAndBrimstone() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        cardsToPreview = new Fireball();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BrimstonePower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            this.isInnate = true;
            baseInfo = info = 1;
        });
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_EFFECT));
    }
}