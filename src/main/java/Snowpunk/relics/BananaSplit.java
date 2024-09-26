package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.util.Wiz;

import static Snowpunk.SnowpunkMod.makeID;

public class BananaSplit extends AbstractEasyRelic {
    public static final String ID = makeID(BananaSplit.class.getSimpleName());

    public BananaSplit() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.atb(new GainSnowballAction(3));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
