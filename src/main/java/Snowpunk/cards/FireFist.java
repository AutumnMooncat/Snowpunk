package Snowpunk.cards;

import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Snowpunk.SnowpunkMod.makeID;

public class FireFist extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FireFist.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 13;

    public FireFist() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = 3;
        secondMagic = baseSecondMagic = 1;
        CardTemperatureFields.addInherentHeat(this, 2);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
//        int numEvaporate = EvaporatePanel.evaporatePile.size();
//        if (numEvaporate > 0)
        Wiz.applyToEnemy(m, new SingePower(m, magicNumber));
        Wiz.applyToEnemy(m, new VulnerablePower(m, secondMagic, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(5));
        addUpgradeData(() -> CardTemperatureFields.addHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> {
            upgradeMagicNumber(1);
            upgradeSecondMagic(1);
        });
    }
}