package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPipe extends AbstractEasyRelic {
    public static final String ID = makeID(BrassPipe.class.getSimpleName());
    public static final int AMOUNT = 5;

    public BrassPipe() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
//        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
//        Wiz.atb(new GainSnowballAction(1));
        Wiz.applyToSelf(new BrassPower(Wiz.adp(), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
