package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.HolidayCheerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;
import static Snowpunk.util.Wiz.atb;

public class ChristmasSpirit extends AbstractEasyRelic {
    public static final String ID = makeID(ChristmasSpirit.class.getSimpleName());

    public int currentCheer = 0;
    public boolean wonFromCheer = false;

    public ChristmasSpirit() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
        counter = 0;
    }

    @Override
    public void atBattleStart() {
        if (counter > 0) {
            currentCheer = counter;
            wonFromCheer = false;
            flash();
            updateHolidayCheer();
        }
    }

    @Override
    public void onVictory() {
        if (wonFromCheer) {
            adp().heal(counter);
            adp().gainGold(counter);
        }
    }

    @Override
    public int changeNumberOfCardsInReward(int amount) {
        if (wonFromCheer)
            amount += 1;
        return amount;
    }

    public void updateHolidayCheer() {
        updateHolidayCheer(0);
    }

    public void updateHolidayCheer(int addAmount) {
        currentCheer += addAmount;
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (adp().hasPower(HolidayCheerPower.POWER_ID))
                adp().getPower(HolidayCheerPower.POWER_ID).amount = currentCheer;
            else
                atb(new ApplyPowerAction(adp(), adp(), new HolidayCheerPower(adp(), currentCheer)));

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (monster.hasPower(HolidayCheerPower.POWER_ID))
                    monster.getPower(HolidayCheerPower.POWER_ID).amount = currentCheer;
                else
                    atb(new ApplyPowerAction(monster, monster, new HolidayCheerPower(monster, currentCheer)));
            }
        }
    }
}
