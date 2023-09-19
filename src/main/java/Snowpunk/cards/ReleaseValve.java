package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
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

public class ReleaseValve extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ReleaseValve.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1, SINGE = 5, UP_SINGE = 2;

    boolean FIREBALL = false;
    private static ArrayList<TooltipInfo> FireTip, FireAndHotTip, BlankTip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (FireTip == null) {
            FireTip = new ArrayList<>();
            FireTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
        }

        if (FireAndHotTip == null) {
            FireAndHotTip = new ArrayList<>();
            FireAndHotTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
            FireAndHotTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.HOT.toLowerCase()), BaseMod.getKeywordDescription(KeywordManager.HOT.toLowerCase())));
        }

        if (BlankTip == null)
            BlankTip = new ArrayList<>();

        if (FIREBALL) {
            if (CardTemperatureFields.getCardHeat(this) != CardTemperatureFields.HOT)
                return FireAndHotTip;
            return FireTip;
        }

        return BlankTip;
    }

    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SINGE;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        Wiz.applyToEnemy(m, new SingePower(m, p, magicNumber));

        if (FIREBALL)
            Wiz.applyToSelf(new FireballPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() ->
        {
            FIREBALL = true;
            uDesc();
        });
        setDependencies(true, 2, 0, 1);
    }
}