package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowballFight extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SnowballFight.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;

    public SnowballFight() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        info = baseInfo = 0;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < getSnow()+info ; i++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
        if (magicNumber > 0) {
            Wiz.applyToSelf(new SnowballPower(p, magicNumber));
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeInfo(1));
        addUpgradeData(this, () -> {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(1);
            uDesc();
        });
    }
}