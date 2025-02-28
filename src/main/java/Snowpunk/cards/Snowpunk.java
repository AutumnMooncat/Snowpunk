package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowpunk extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Snowpunk.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 1, SNOW = 1, UP_SNOW = 1;

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

    public Snowpunk() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int gears = getGears();
        if (gears > 0) {
            Wiz.applyToSelf(new BrassPower(Wiz.adp(), gears));
            Wiz.applyToSelf(new BrassPower(Wiz.adp(), gears));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}