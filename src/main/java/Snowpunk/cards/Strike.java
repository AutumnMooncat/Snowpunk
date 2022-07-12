package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Strike extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Strike.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 6;
    private static final int UP_DMG = 3;

    public Strike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = 1;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i ++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG)); // 0
        addUpgradeData(this, () -> upgradeBaseCost(0)); // 1
        addUpgradeData(this, () -> {
            upgradeDamage(-2);
            upgradeMagicNumber(1);
        }); // 2
        /*addUpgradeData(this, () -> upgradeDamage(UP_DMG + 2), 0); // 3
        addUpgradeData(this, () -> {
            upgradeDamage(-2);
            upgradeMagicNumber(1);
        }, 0); // 4
        addUpgradeData(this, () -> upgradeDamage(4), 1); // 5
        addUpgradeData(this, () -> upgradeDamage(4), 1); // 6
        addUpgradeData(this, () -> upgradeDamage(4), 2); // 7
        addUpgradeData(this, () -> upgradeDamage(4), 2); // 8
        addUpgradeData(this, () -> upgradeDamage(4), 3, 4); // 9
        addUpgradeData(this, () -> upgradeDamage(4), 5, 6); // 10
        addUpgradeData(this, () -> upgradeDamage(4), 7, 8); // 11
        addUpgradeData(this, () -> upgradeDamage(4), 9, 10); // 12
        addUpgradeData(this, () -> upgradeDamage(4), 10, 11); // 13
        addUpgradeData(this, () -> upgradeDamage(4), 12, 13); // 13*/

    }
}