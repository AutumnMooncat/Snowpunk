package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowStack extends AbstractEasyCard {
    public final static String ID = makeID(SnowStack.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int BLK = 2;
    private static final int UP_BLK = 1;

    public SnowStack() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getSnow() * block > 0) {
            Wiz.atb(new GainBlockAction(p, block*getSnow()));
        }
    }

    public void upp() {
        CardTemperatureFields.addInherentHeat(this, -1);
        upgradeBlock(UP_BLK);
    }
}