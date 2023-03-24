package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.WidgetsPower;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import Snowpunk.util.Wiz;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCreamSandwich extends AbstractEasyRelic implements XCostModifier, SnowAmountModifier {
    public static final String ID = makeID(IceCreamSandwich.class.getSimpleName());
    public static final int AMOUNT = 1;

    public IceCreamSandwich() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public int modifyX(AbstractCard abstractCard) {
        flash();
        return AMOUNT;
    }

    @Override
    public boolean xCostModifierActive(AbstractCard c) {
        return !SCostFieldPatches.SCostField.isSCost.get(c);
    }

    @Override
    public int modifySnow() {
        return AMOUNT;
    }
}
