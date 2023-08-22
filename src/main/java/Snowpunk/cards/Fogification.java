package Snowpunk.cards;

import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Fogification extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Fogification.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 8, UP_BLOCK = 2;

    public Fogification() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}