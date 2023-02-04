package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
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

    private static final int COST = 1, DMG = 4, UP_DMG = 3, BURN_AMOUNT = 4, UP_BURN = 3;

    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = BURN_AMOUNT;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new SingePower(m, p, magicNumber));
        //Wiz.atb(new ModCardTempAction(this, 1));

        //Wiz.applyToSelf(new FireballPower(p, FIREBALL));
        //    Wiz.applyToSelf(new OverheatNextCardPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeMagicNumber(UP_BURN));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        setDependencies(true, 2, 0, 1);
    }
}