package Snowpunk.cards;

import Snowpunk.actions.IncreaseModifiersAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
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

public class FinalTouch extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FinalTouch.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
            return Tooltip;
    }

    public FinalTouch() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        block = baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new IncreaseModifiersAction(false, getSnow() + magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(3));
        addUpgradeData(() -> CardTemperatureFields.addHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeMagicNumber(1));
    }
}