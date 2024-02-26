package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.ScrapPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamPipe extends AbstractEasyRelic {
    public static final String ID = makeID(SteamPipe.class.getSimpleName());

    public static final int AMOUNT = 4;

    public SteamPipe() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }


    @Override
    public void atTurnStart() {
        flash();
        Wiz.applyToSelf(new BrassPower(Wiz.adp(), AMOUNT));
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
