package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class JingleBells extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(JingleBells.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLOCK = 12, UPG_BLOCK = 4, HOLLY = 3, UP_HOLLY = 1;

    private boolean tinkerSelf = false;

    public JingleBells() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (getSnow() + magicNumber > 0)
            Wiz.atb(new DrawCardAction(getSnow() + magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UPG_BLOCK));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeMagicNumber(1));
    }
}