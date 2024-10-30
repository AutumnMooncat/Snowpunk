package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.util.Wiz;

import static Snowpunk.SnowpunkMod.makeID;

public class ChemicalS extends AbstractEasyRelic {
    public static final String ID = makeID(ChemicalS.class.getSimpleName());

    public ChemicalS() {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
