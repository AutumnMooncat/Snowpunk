package Snowpunk.cards;

import Snowpunk.actions.RushdownAction;
import Snowpunk.cardmods.FrostMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Sledding extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Sledding.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 8, UP_DMG = 2, BLOCK = 5, UP_BLOCK = 2, BLOCK_PER_HIT = 2;

    boolean perhit = false;

    public Sledding() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        block = baseBlock = BLOCK;
        isMultiDamage = true;
        CardModifierManager.addModifier(this, new FrostMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (perhit) {
            Wiz.atb(new RushdownAction(p, multiDamage, damageTypeForTurn, secondBlock));
        } else {
            Wiz.atb(new RushdownAction(p, multiDamage, damageTypeForTurn));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, this::upgrade1);
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(this, () -> {
            perhit = true;
            this.baseSecondBlock = this.secondBlock = 0;
            upgradeSecondBlock(BLOCK_PER_HIT);
            uDesc();
        });
    }

    private void upgrade1() {
        upgradeBlock(UP_BLOCK);
        upgradeDamage(UP_DMG);
    }
}