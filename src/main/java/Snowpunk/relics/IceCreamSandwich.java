package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCreamSandwich extends AbstractEasyRelic {
    public static final String ID = makeID(IceCreamSandwich.class.getSimpleName());
    public static final int AMOUNT = 3;

    public IceCreamSandwich() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
//        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
//        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.atb(new GainSnowballAction(1));
//        Wiz.applyToSelf(new BrassPower(Wiz.adp(), AMOUNT));
//        Wiz.applyToSelf(new PreventBrassConsumptionPower(Wiz.adp(), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
