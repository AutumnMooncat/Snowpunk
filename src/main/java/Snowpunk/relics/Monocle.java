package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.GearNextPower;
import Snowpunk.powers.HollyPower;
import Snowpunk.util.Wiz;

import static Snowpunk.SnowpunkMod.makeID;

public class Monocle extends AbstractEasyRelic {
    public static final String ID = makeID(Monocle.class.getSimpleName());
    public static final int AMOUNT = 2;

    public Monocle() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new GearNextPower(Wiz.adp(), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
