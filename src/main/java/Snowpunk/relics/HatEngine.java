package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.relics.interfaces.OnEvaporateRelic;
import Snowpunk.util.Wiz;
import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class HatEngine extends AbstractEasyRelic implements OnEvaporateRelic {
    public static final String ID = makeID(HatEngine.class.getSimpleName());
    public static final int AMOUNT = 3;


    public HatEngine() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        grayscale = false;
    }

    @Override
    public void atBattleStart() {
        grayscale = false;
    }

    @Override
    public void onVictory() {
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEvaporate(AbstractCard card) {
        if (!grayscale) {
            flash();
            Wiz.atb(new GainEnergyAction(1));
            Wiz.atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            Wiz.atb(new DrawCardAction(1));
            grayscale = true;
        }
    }
}
