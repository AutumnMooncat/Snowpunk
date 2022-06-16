package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowFort extends AbstractEasyCard {
    public final static String ID = makeID(SnowFort.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = -1;
    private static final int BLK = 7;
    private static final int UP_BLK = 3;

    public SnowFort() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < getSnow() ; i++) {
            blck();
        }
        Wiz.atb(new RemoveSpecificPowerAction(p, p, SnowballPower.POWER_ID));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }
}