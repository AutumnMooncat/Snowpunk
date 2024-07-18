package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.patches.CardTemperatureFields;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCreamSandwich extends AbstractEasyRelic/* implements ModifySnowballsRelic*/ {
    public static final String ID = makeID(IceCreamSandwich.class.getSimpleName());
    public static final int AMOUNT = 1;

    public IceCreamSandwich() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }


    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.group.size() > 0) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (CardTemperatureFields.getCardHeat(card) == CardTemperatureFields.COLD)
                    card.retain = true;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
