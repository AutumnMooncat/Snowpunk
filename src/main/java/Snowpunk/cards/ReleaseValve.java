package Snowpunk.cards;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cardmods.parts.BurnDamageMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.BurnPower;
import Snowpunk.powers.HeatNextCardPower;
import Snowpunk.powers.OverheatNextCardPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ReleaseValve extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ReleaseValve.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 10, UP_DMG = 4, HEAT_NEXT = 1, UP_HEAT_NEXT = 1, BURN_AMOUNT = 4;

    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = HEAT_NEXT;
        secondMagic = baseSecondMagic = 0;
        baseInfo = info = 0;
        CardModifierManager.addModifier(this, new WhistolMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToSelf(new HeatNextCardPower(p, magicNumber));
        if (secondMagic > 0)
            Wiz.applyToEnemy(m, new BurnPower(m, p, secondMagic));
        //    Wiz.applyToSelf(new OverheatNextCardPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_HEAT_NEXT));
        addUpgradeData(this, () -> upgrade2());
        addUpgradeData(this, () -> upgrade3());
    }

    private void upgrade2() {
        upgradeSecondMagic(BURN_AMOUNT);
        uDesc();
    }

    private void upgrade3() {
        upgradeDamage(-UP_DMG);
        upgradeBaseCost(1);
    }
}