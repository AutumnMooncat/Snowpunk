package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.ScrapPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;

import static Snowpunk.SnowpunkMod.makeID;

public class ScrapHeap extends AbstractEasyRelic {
    public static final String ID = makeID(ScrapHeap.class.getSimpleName());

    public static final int AMOUNT = 3;

    public ScrapHeap() {
        super(ID, RelicTier.SHOP, LandingSound.HEAVY, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    public void atTurnStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        Wiz.applyToSelf(new ScrapPower(Wiz.adp(), AMOUNT));
    }

    public boolean canUseCampfireOption(AbstractCampfireOption option) {
        if (option instanceof SmithOption && option.getClass().getName().equals(SmithOption.class.getName())) {
            ((SmithOption)option).updateUsability(false);
            return false;
        } else {
            return true;
        }
    }
}
