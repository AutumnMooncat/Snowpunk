package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.CarolingDrawPower;
import Snowpunk.powers.CarolingSnowballPower;
import Snowpunk.powers.HollyPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Caroling extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Caroling.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    public Caroling() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 5;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new HollyPower(p, secondMagic));
        Wiz.applyToSelf(new CarolingDrawPower(p, magicNumber));
    }

    private static ArrayList<TooltipInfo> ChristmasTooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (ChristmasTooltip == null) {
            ChristmasTooltip = new ArrayList<>();
            ChristmasTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.CHRISTMAS_SPIRIT), BaseMod.getKeywordDescription(KeywordManager.CHRISTMAS_SPIRIT)));
        }
        return ChristmasTooltip;
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(this, () -> upgradeMagicNumber(1));
        addUpgradeData(this, () -> upgradeSecondMagic(2));
    }
}