package Snowpunk.cards;

import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.damageMods.CauterizeDamage;
import Snowpunk.damageMods.PiercingDamage;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Cauterize extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Cauterize.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 8, UP_DMG = 3, SINGE = 6, UP_SINGE = 2, FIRE = 1, UP_FIRE = 1;

    public Cauterize() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        DamageModifierManager.addModifier(this, new CauterizeDamage());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (magicNumber > 0)
            Wiz.applyToSelf(new FireballPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> {
            magicNumber = baseMagicNumber = 0;
            upgradeMagicNumber(UP_FIRE);
            uDesc();
        });
    }
}