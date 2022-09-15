package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BurnPower;
import Snowpunk.powers.FireballPower;
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

    private static final int COST = 1, DMG = 6, UP_DMG = 4, BURN_AMOUNT = 4, BURN_UP = 2;

    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = BURN_AMOUNT;
        baseInfo = info = 0;
        CardModifierManager.addModifier(this, new WhistolMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        Wiz.atb(new ModCardTempAction(this, 1));

        //Wiz.applyToSelf(new FireballPower(p, magicNumber));
        //    Wiz.applyToSelf(new OverheatNextCardPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeMagicNumber(BURN_UP));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1));
    }
}