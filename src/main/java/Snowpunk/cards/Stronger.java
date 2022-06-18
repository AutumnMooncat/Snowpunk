package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.StrongerPower;
import Snowpunk.powers.WinterStormPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Stronger extends AbstractEasyCard {
    public final static String ID = makeID(Stronger.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 2;
    private static final int STACKS = 1;
    private static final int UP_STACKS = 1;

    public Stronger() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new StrongerPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
        //upgradeMagicNumber(UP_STACKS);
    }
}