package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.SnowballPower;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.powers.TinkerNextCardPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPipeRelic extends AbstractEasyRelic {
    public static final String ID = makeID(BrassPipeRelic.class.getSimpleName());

    public BrassPipeRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.applyToSelf(new SparePartsPower(Wiz.adp(), 2));
    }
}
