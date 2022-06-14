package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Frostbite extends AbstractEasyCard {
    public final static String ID = makeID(Frostbite.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 2;

    public Frostbite() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = getSnow() + (upgraded ? 1 : 0);
        if (amount > 0) {
            Wiz.applyToEnemy(m, new FrostbitePower(m, p, amount));
            Wiz.applyToEnemy(m, new FrostbitePower(m, p, amount));
        }
        Wiz.atb(new RemoveSpecificPowerAction(p, p, SnowballPower.POWER_ID));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }
}