package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamPipe extends AbstractEasyRelic {
    public static final String ID = makeID(SteamPipe.class.getSimpleName());

    public static final int AMOUNT = 1;

    public SteamPipe() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    public void atTurnStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        Wiz.applyToSelf(new SparePartsPower(Wiz.adp(), AMOUNT));
    }

    @Override
    public void obtain() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic(BrassPipe.ID)) {
            for (int i = 0; i < p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(BrassPipe.ID)) {
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BrassPipe.ID);
    }
}
