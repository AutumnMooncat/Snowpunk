package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.relics.interfaces.OnClankRelic;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class Lightbulb extends AbstractEasyRelic implements OnClankRelic {
    public static final String ID = makeID(Lightbulb.class.getSimpleName());

    boolean clanked = false;

    public Lightbulb() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0];
        clanked = false;
    }

    @Override
    public void atTurnStart() {
        clanked = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onClank(AbstractCard card, AbstractCreature target) {
        if (!clanked) {
            clanked = true;
            Wiz.atb(new GainEnergyAction(1));
            flash();
        }
    }
}
