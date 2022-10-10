package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.ChristmasSpiritPower;
import Snowpunk.powers.HolidayCheerPower;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;
import static Snowpunk.util.Wiz.atb;

public class ChristmasSpirit extends AbstractEasyRelic {
    public static final String ID = makeID(ChristmasSpirit.class.getSimpleName());

    public int currentCheer;

    public ChristmasSpirit() {
        this(0);
    }

    public ChristmasSpirit(int cheer) {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
        counter = currentCheer = cheer;
        grayscale = false;
    }

    @Override
    public void onEquip() {
        updateSpirit();
    }

    @Override
    public void onVictory() {
        grayscale = true;
        counter = currentCheer = -1;
    }

    @Override
    public void atBattleStartPreDraw() {
        grayscale = false;
        counter = currentCheer = 0;
    }


    public void updateSpirit() {
        updateSpirit(0);
    }

    public void updateSpirit(int addAmount) {
        currentCheer += addAmount;
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (adp().hasPower(ChristmasSpiritPower.POWER_ID)) {
                if (adp().getPower(ChristmasSpiritPower.POWER_ID).amount < currentCheer)
                    adp().getPower(ChristmasSpiritPower.POWER_ID).flash();
                adp().getPower(ChristmasSpiritPower.POWER_ID).amount = currentCheer;
            } else
                atb(new ApplyPowerAction(adp(), adp(), new ChristmasSpiritPower(adp(), currentCheer)));

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.escaped && !monster.isDead && !monster.isEscaping) {
                    if (monster.hasPower(ChristmasSpiritPower.POWER_ID)) {
                        if (monster.getPower(ChristmasSpiritPower.POWER_ID).amount < currentCheer)
                            monster.getPower(ChristmasSpiritPower.POWER_ID).flash();
                        monster.getPower(ChristmasSpiritPower.POWER_ID).amount = currentCheer;
                    } else
                        atb(new ApplyPowerAction(monster, monster, new ChristmasSpiritPower(monster, currentCheer)));
                }
            }
        }
        counter = currentCheer;
    }
}
