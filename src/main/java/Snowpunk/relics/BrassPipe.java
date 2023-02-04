package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPipe extends AbstractEasyRelic {
    public static final String ID = makeID(BrassPipe.class.getSimpleName());

    public BrassPipe() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new SparePartsPower(Wiz.adp(), 2));
    }
}
