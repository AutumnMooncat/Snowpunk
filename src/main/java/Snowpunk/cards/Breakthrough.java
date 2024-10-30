package Snowpunk.cards;

import Snowpunk.actions.IncreaseModifiersAction;
import Snowpunk.actions.IncreasePlatingAndGearsAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Breakthrough extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Breakthrough.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public Breakthrough() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 12;
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new IncreasePlatingAndGearsAction(false, -1, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            upgradeDamage(2);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        setDependencies(true, 2, 0, 1);
    }
}