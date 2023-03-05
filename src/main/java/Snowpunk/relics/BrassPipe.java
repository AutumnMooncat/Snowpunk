package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.WidgetsPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPipe extends AbstractEasyRelic {
    public static final String ID = makeID(BrassPipe.class.getSimpleName());
    public static final int AMOUNT = 2;

    public BrassPipe() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new WidgetsPower(Wiz.adp(), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
