package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class PackedIce extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(PackedIce.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;
    private static final int BLOCK = 6;
    private static final int UP_BLOCK = 3;

    public PackedIce() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonusSnow = 0;
        if (magicNumber > 0)
            bonusSnow = magicNumber;
        for (int i = 0; i < getSnow() + bonusSnow; i++) {
            blck();
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(1);
        });
        setExclusions(1, 2);
    }
}