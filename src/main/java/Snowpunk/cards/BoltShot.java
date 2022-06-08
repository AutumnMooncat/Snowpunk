package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.HeatNextCardPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Snowpunk.SnowpunkMod.makeID;

public class BoltShot extends AbstractEasyCard {
    public final static String ID = makeID(BoltShot.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 8;
    private static final int UP_DMG = 3;
    private static final int VULN = 1;

    public BoltShot() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = VULN;
        tags.add(CustomTags.GUN);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}