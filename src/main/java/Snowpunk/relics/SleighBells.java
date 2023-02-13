package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

import static Snowpunk.SnowpunkMod.makeID;

public class SleighBells extends AbstractEasyRelic {
    public static final String ID = makeID(SleighBells.class.getSimpleName());
    public static final int AMOUNT = 4;

    public SleighBells() {
        super(ID, RelicTier.COMMON, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void atBattleStart() {
        flash();
        Wiz.applyToSelfTop(new SparePartsPower(Wiz.adp(), 4));
        Wiz.att(new RelicAboveCreatureAction(Wiz.adp(), this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }
}
