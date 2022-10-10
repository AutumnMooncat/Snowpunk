package Snowpunk.cards;

import Snowpunk.actions.GainWaterAction;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class WaterTank extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(WaterTank.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public WaterTank() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainWaterAction(this, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeMagicNumber(1));
        setExclusions(1, 2);
    }
}