package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ColdShoulder extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ColdShoulder.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CHILL = 3, UP_CHILL = 3, WEAK = 1;

    public ColdShoulder() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHILL;
        secondMagic = baseSecondMagic = WEAK;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
        Wiz.applyToEnemy(m, new WeakPower(m, secondMagic, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_CHILL));
        addUpgradeData(() -> upgradeSecondMagic(1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}