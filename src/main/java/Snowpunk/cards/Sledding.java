package Snowpunk.cards;

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

    private static final int COST = 2, DMG = 8, UP_DMG = 2, BLOCK = 5, UP_BLOCK = 2;

    public Sledding() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        block = baseBlock = BLOCK;
        isMultiDamage = true;
        CardModifierManager.addModifier(this, new FrostMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((timesUpgraded & 1 << 2) != 0) {
            for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
                if (!AbstractDungeon.getMonsters().monsters.get(i).isDead)
                    blck();
            }
        } else
            blck();

        Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgrade1());
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(this, () -> uDesc());
    }

    private void upgrade1() {
        upgradeBlock(UP_BLOCK);
        upgradeDamage(UP_DMG);
    }
}