package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.RushdownAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.ReverseNextClankPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class SleighRide extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(SleighRide.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, UP_COST = 2, DMG = 20, UP_DMG = 3;

    public SleighRide() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
    }

//    private static ArrayList<TooltipInfo> Tooltip;
//
//    @Override
//    public List<TooltipInfo> getCustomTooltips() {
//        if (Tooltip == null) {
//            Tooltip = new ArrayList<>();
//            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
//        }
//        return Tooltip;
//    }

//    @Override
//    public void triggerWhenDrawn() {
//        super.triggerWhenDrawn();
//        applyPowers();
//    }
//
//    @Override
//    public void atTurnStart() {
//        resetAttributes();
//        applyPowers();
//    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int chill = -1;
        if (p.hasPower(ReverseNextClankPower.POWER_ID)) {
            if (p.getPower(ReverseNextClankPower.POWER_ID).amount > 0)
                chill = 4;
        }
        Wiz.atb(new RushdownAction(p, multiDamage, damageTypeForTurn, chill));

        Wiz.atb(new ClankAction(this));
    }

//    @Override
//    public void applyPowers() {
//        super.applyPowers();
//        int newCost = Math.max(COST - getSnow(), 0);
//        if (costForTurn > newCost)
//            setCostForTurn(newCost);
//    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
        setDependencies(true, 3, 2);
        setDependencies(true, 4, 3);
        setDependencies(true, 5, 4);
    }

    @Override
    public void onClank(AbstractMonster target) {
        Wiz.applyToSelf(new ChillPower(Wiz.adp(), 4));
    }

    @Override
    public void unClank(AbstractMonster target) {

    }
}