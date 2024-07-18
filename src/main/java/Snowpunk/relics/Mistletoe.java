package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.HollyPower;
import Snowpunk.util.Wiz;

import static Snowpunk.SnowpunkMod.makeID;

public class Mistletoe extends AbstractEasyRelic {
    public static final String ID = makeID(Mistletoe.class.getSimpleName());
    public static final int AMOUNT = 5;

    public Mistletoe() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new HollyPower(Wiz.adp(), AMOUNT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
