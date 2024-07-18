package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.util.Wiz;

import static Snowpunk.SnowpunkMod.makeID;

public class SpareWrench extends AbstractEasyRelic {
    public static final String ID = makeID(SpareWrench.class.getSimpleName());

    public SpareWrench() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new PermWrenchPower(Wiz.adp(), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
