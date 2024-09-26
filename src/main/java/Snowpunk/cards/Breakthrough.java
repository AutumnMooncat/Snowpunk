package Snowpunk.cards;

import Snowpunk.actions.IncreaseModifiersAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
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
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new IncreaseModifiersAction(false, -1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(4));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}