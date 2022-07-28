package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.powers.NextTurnPowerPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowFort extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SnowFort.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLK = 12, UP_BLK = 4, MAGIC = 2, UP_MAGIC = 1;

    public SnowFort() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        magicNumber = baseMagicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new NextTurnPowerPower(p, new SnowballPower(p, magicNumber)));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBlock(UP_BLK));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MAGIC));
    }
}