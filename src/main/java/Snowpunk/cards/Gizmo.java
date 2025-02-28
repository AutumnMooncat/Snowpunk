package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Gizmo extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Gizmo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 8, BLOCK = 8, UP_DMG = 5, UP_BLOCK = 5;

    public Gizmo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new GearMod(3));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        int numGears = getGears();
        if (numGears > 0)
            Wiz.applyToSelf(new BrassPower(p, numGears));
        if (magicNumber > 0)
            Wiz.atb(new ApplyCardModifierAction(this, new GearMod(magicNumber)));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(2);
            upgradeBlock(2);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> {
            upgradeMagicNumber(1);
            CardModifierManager.addModifier(this, new GearMod(1));
        });
    }
}