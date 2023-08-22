package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatTransfer extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(HeatTransfer.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CHILL = 7, UP_CHILL = 3, FIRE = 1, UP_FIRE = 1;

    private boolean aoe;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
        }
        return Tooltip;
    }

    public HeatTransfer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHILL;
        baseSecondMagic = secondMagic = FIRE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
        Wiz.applyToSelf(new FireballPower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_CHILL));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeSecondMagic(UP_FIRE));
        setExclusions(1, 2);
    }
}