package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.relics.interfaces.ModifySnowballsRelic;
import Snowpunk.util.Wiz;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCreamSandwich extends AbstractEasyRelic implements ModifySnowballsRelic {
    public static final String ID = makeID(IceCreamSandwich.class.getSimpleName());
    public static final int AMOUNT = 1;

    public IceCreamSandwich() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
/*
    @Override
    public int modifyX(AbstractCard abstractCard) {
        flash();
        return AMOUNT;
    }

    @Override
    public boolean xCostModifierActive(AbstractCard c) {
        return !SCostFieldPatches.SCostField.isSCost.get(c);
    }*/

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
    }
}
