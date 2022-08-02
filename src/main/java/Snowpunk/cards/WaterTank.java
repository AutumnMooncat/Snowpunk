package Snowpunk.cards;

import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class WaterTank extends AbstractMultiUpgradeCard implements MultiTempEffectCard {
    public final static String ID = makeID(WaterTank.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int MULTI = 2;
    private static final int UP_MULTI = 1;

    public WaterTank() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MULTI;
        CardModifierManager.addModifier(this, new VentMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public int tempEffectAmount() {
        return magicNumber;
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1), new int[]{}, new int[]{2});
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MULTI));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1), new int[]{}, new int[]{0});
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1), 0);
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1), 2);
    }
}