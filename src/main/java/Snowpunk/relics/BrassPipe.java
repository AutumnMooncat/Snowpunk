package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPipe extends AbstractEasyRelic {
    public static final String ID = makeID(BrassPipe.class.getSimpleName());
    public static final int AMOUNT = 5;

    public BrassPipe() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new BrassPower(Wiz.adp(), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
