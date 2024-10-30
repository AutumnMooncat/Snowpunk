package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
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

    private static final int COST = 2, DMG = 9, BLOCK = 9, UP_DMG = 5, UP_BLOCK = 5;

    public Gizmo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new ApplyCardModifierAction(this, new PlateMod(magicNumber)));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            CardModifierManager.addModifier(this, new PlateMod(1));
            upgradeMagicNumber(1);
        });
        addUpgradeData(() -> {
            CardModifierManager.addModifier(this, new PlateMod(1));
            upgradeMagicNumber(1);
        });
        addUpgradeData(() -> {
            CardModifierManager.addModifier(this, new PlateMod(1));
            upgradeMagicNumber(1);
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}