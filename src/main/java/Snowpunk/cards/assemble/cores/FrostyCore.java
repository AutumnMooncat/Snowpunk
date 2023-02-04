package Snowpunk.cards.assemble.cores;

import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class FrostyCore extends CoreCard {
    public static final String ID = makeID(FrostyCore.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public FrostyCore() {
        super(ID, COST, TYPE, EffectTag.MOD);
        CardModifierManager.addModifier(this, new ClockworkMod());
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        for (CoreCard core : coreCards) {
            if (CardTemperatureFields.getCardHeat(core) > 0)
                return true;
        }
        int totalCost = 0;
        for (CoreCard core : coreCards) {
            totalCost += core.cost;
        }
        return totalCost < 1;
    }

    @Override
    public void setStats(AbstractCard card) {
        CardModifierManager.addModifier(card, new ClockworkMod());
    }
}
