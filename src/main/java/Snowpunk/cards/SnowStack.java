package Snowpunk.cards;

import Snowpunk.actions.GainSnowballAction;
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

public class SnowStack extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SnowStack.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BLOCK = 10;
    private static final int UP_BLOCK = 4;

    public SnowStack() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLOCK;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    private static ArrayList<TooltipInfo> Tooltip, ToolTipBlank;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (ToolTipBlank == null)
            ToolTipBlank = new ArrayList<>();
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        if (magicNumber > 0)
            return Tooltip;
        return ToolTipBlank;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (magicNumber > 0)
            Wiz.atb(new GainSnowballAction((magicNumber)));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(1);
        });
        setDependencies(true, 2, 0, 1);
    }
}