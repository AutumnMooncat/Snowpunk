package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.OnTinkeredCard;
import Snowpunk.cards.parts.AbstractPartCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassKnuckles extends AbstractMultiUpgradeCard implements OnTinkeredCard {
    public final static String ID = makeID(BrassKnuckles.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int DMG = 4;
    private static final int UP_DMG = 2;
    private static final int DOWN_DMG = -1;
    private static final int BOOST = 2;

    private boolean third = false;

    public BrassKnuckles() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = BOOST;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (third) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
    }

    @Override
    public void onTinkered(AbstractPartCard part) {
        upgradeDamage(magicNumber);
        superFlash();
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            third = true;
            upgradeDamage(DOWN_DMG);
            uDesc();
        });
    }
}