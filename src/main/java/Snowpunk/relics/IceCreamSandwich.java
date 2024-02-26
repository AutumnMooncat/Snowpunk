package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.relics.interfaces.ModifySnowballsRelic;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCreamSandwich extends AbstractEasyRelic/* implements ModifySnowballsRelic*/ {
    public static final String ID = makeID(IceCreamSandwich.class.getSimpleName());
    public static final int AMOUNT = 1;

    public IceCreamSandwich() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        Wiz.atb(new GainSnowballAction(1));
    }
    /*
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public int modifySnow() {
        return 1;
    }*/
}
