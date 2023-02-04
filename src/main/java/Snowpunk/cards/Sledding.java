package Snowpunk.cards;

import Snowpunk.actions.RushdownAction;
import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Sledding extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Sledding.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 8, UP_DMG = 4, BLOCK = 3, UP_BLOCK = 2;

    public Sledding() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        block = baseBlock = BLOCK;
        isMultiDamage = true;
        CardModifierManager.addModifier(this, new ClockworkMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new RushdownAction(p, multiDamage, damageTypeForTurn, block));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
    }
}