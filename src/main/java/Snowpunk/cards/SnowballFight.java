package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.AbstractSCostMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowballFight extends AbstractSCostMultiUpgradeCard {

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    public final static String ID = makeID(SnowballFight.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;
    public SnowballFight() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonusSnow = 0;
        if (magicNumber > 0)
            bonusSnow = magicNumber;
        for (int i = 0; i < getSnow() + bonusSnow; i++)
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(1);
        });
        setExclusions(1, 2);
    }
}