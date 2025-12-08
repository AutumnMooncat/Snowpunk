package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierWithVisualAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.actions.RushdownAction;
import Snowpunk.actions.SleighRideAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.ReverseNextClankPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class SleighRide extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SleighRide.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 12, UP_DMG = 4;

    public SleighRide() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new RushdownAction(p, multiDamage, damageTypeForTurn, -1));
        //Wiz.atb(new SleighRideAction(magicNumber));
        //Wiz.atb(new ApplyCardModifierWithVisualAction(this, new PlateMod(magicNumber)));
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = baseDamage;
        baseDamage += getHolly();

        super.calculateCardDamage(mo);

        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = baseDamage;
        baseDamage += getHolly();

        super.applyPowers();

        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        setDependencies(true, 2, 0, 1);
    }
}