package Snowpunk.cards;

import Snowpunk.actions.GainSnowFromColdAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Eggnog extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Eggnog.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Eggnog() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
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

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainSnowFromColdAction((Math.max(magicNumber, 0))));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() ->
        {
            magicNumber = baseMagicNumber = 0;
            upgradeMagicNumber(1);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}