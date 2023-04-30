package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ColdCore extends CoreCard {
    public static final String ID = makeID(ColdCore.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public ColdCore() {
        super(ID, COST, TYPE, EffectTag.MOD, EffectTag.COLD);
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.HOT)))
            return true;
        return false;
    }

    @Override
    public void setStats(AbstractCard card) {
        CardTemperatureFields.addInherentHeat(card, -1 - CardTemperatureFields.getCardHeat(card));
    }
}
