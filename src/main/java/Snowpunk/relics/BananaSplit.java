package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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

//    @Override
//    public void obtain() {
//        AbstractPlayer p = AbstractDungeon.player;
//        if (p.hasRelic(IceCreamSandwich.ID)) {
//            for (int i = 0; i < p.relics.size(); ++i) {
//                if (p.relics.get(i).relicId.equals(IceCreamSandwich.ID)) {
//                    instantObtain(p, i, true);
//                    break;
//                }
//            }
//        } else {
//            super.obtain();
//        }
//    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

//    @Override
//    public boolean canSpawn() {
//        return AbstractDungeon.player.hasRelic(IceCreamSandwich.ID);
//    }
}
