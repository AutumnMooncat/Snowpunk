package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.TinkerNextCardPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassPipeRelic extends AbstractEasyRelic {
    public static final String ID = makeID(BrassPipeRelic.class.getSimpleName());

    public BrassPipeRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractPlayer player = AbstractDungeon.player;
        player.addPower(new TinkerNextCardPower(player, 1, false));
        //AbstractDungeon.actionManager.addToBottom(new VacantMillAction(counter + AbstractVacantCard.GetBonusMillAmount()));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VoidPower(player, player, VOID_AMOUNT), VOID_AMOUNT));
    }
}
