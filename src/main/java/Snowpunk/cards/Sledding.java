package Snowpunk.cards;

import Snowpunk.actions.RushdownAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Sledding extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Sledding.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3, UP_COST = 2, DMG = 14, UP_DMG = 4;
    boolean upCost = false;

    public Sledding() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
    }

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        applyPowers();
    }

    @Override
    public void atTurnStart() {
        resetAttributes();
        applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new RushdownAction(p, multiDamage, damageTypeForTurn, -1));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int newCost = Math.max((upCost ? UP_COST : COST) - getSnow(), 0);
        if (costForTurn > newCost)
            setCostForTurn(newCost);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        setDependencies(true, 2, 1);
    }
}