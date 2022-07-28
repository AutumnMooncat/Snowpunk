package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.FrostMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class IcicleSpear extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(IcicleSpear.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 6, UP_DMG = 3, MAGIC = 3;

    public IcicleSpear() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = MAGIC;
        info = baseInfo = 0;
        CardModifierManager.addModifier(this, new FrostMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        Wiz.atb(new ModCardTempAction(this, -1));
        if (info > 0)
            Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgrade2());
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
    }

    private void upgrade2() {
        upgradeInfo(1);
        uDesc();
    }
}