package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FireWallPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class FireWall extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FireWall.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLOCK = 10, SINGE = 5, UP_BLOCK = 4, UP_SINGE = 2;

    public FireWall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber = magicNumber = SINGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new FireWallPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
    }
}