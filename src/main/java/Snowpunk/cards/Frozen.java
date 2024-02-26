package Snowpunk.cards;

import Snowpunk.actions.ConvertSingeToChillAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FrozenPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Frozen extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Frozen.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 8, FROZEN = 1, CHILL = 3;
    private boolean singeToChill;

    public Frozen() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = FROZEN;
        secondMagic = baseSecondMagic = CHILL;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
        singeToChill = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        Wiz.applyToEnemy(m, new FrozenPower(m, magicNumber));
        Wiz.applyToEnemy(m, new ChillPower(m, secondMagic));
        if (singeToChill)
            Wiz.atb(new ConvertSingeToChillAction(m));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(3);
            upgradeSecondMagic(1);
        });
        addUpgradeData(() ->
        {
            singeToChill = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(1));
    }
}