package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class NutCracker extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(NutCracker.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 3;
    private static final int UP_DMG = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;
    private static final int HITS = 1;
    private static final int UP_HITS = 1;

    private boolean vuln = false;

    public NutCracker() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = EFFECT;
        secondMagic = baseSecondMagic = HITS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < secondMagic ; i++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
        Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
        if (vuln) {
            Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            upgradeDamage(UP_DMG);
            upgradeMagicNumber(UP_EFFECT);
        });
        addUpgradeData(this, () -> {
            vuln = true;
            uDesc();
        });
        addUpgradeData(this, () -> upgradeSecondMagic(UP_HITS));
    }
}