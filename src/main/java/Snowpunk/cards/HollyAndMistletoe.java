package Snowpunk.cards;

import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static Snowpunk.SnowpunkMod.makeID;

public class HollyAndMistletoe extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(HollyAndMistletoe.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int MAGIC = 6;
    private static final int UP_MAGIC = 4;

    public HollyAndMistletoe() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseInfo = info = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HolidayCheerUpAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MAGIC));
    }
}