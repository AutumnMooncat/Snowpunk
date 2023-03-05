package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.SnowpunkPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowpunk extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Snowpunk.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2, UP_COST = 1, SNOW = 1, UP_SNOW = 1;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
        }
        return Tooltip;
    }

    public Snowpunk() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = SNOW;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SnowpunkPower(p, 1));
        Wiz.applyToSelf(new SnowballPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {

        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(UP_SNOW));
    }
}