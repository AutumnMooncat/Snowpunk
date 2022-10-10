package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.powers.ChristmasCookiePower;
import Snowpunk.powers.HolidayCheerPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;
import static Snowpunk.util.Wiz.atb;
/*
public class ARCHIVED_ChristmasSpirit extends AbstractEasyRelic {
    public static final String ID = makeID(ARCHIVED_ChristmasSpirit.class.getSimpleName());

    public int currentCheer = 0;
    public boolean prevRoomBattle = false, killThisCombat = false;

    public ARCHIVED_ChristmasSpirit() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheConductor.Enums.SNOWY_BLUE_COLOR);
        counter = 0;
    }


    @Override
    public void onEquip() {
        for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
            AbstractMonster m = AbstractDungeon.getMonsters().monsters.get(i);
            if (m.isDead) {
                if (m.currentHealth == 0) {
                    flash();
                    if (counter < 0)
                        counter = 0;
                    killThisCombat = true;
                    grayscale = true;
                    Wiz.atb(new ChangeChristmasSpiritAction(-1));
                }
            }
        }
    }

    @Override
    public void atBattleStart() {
        if (counter > 0) {
            currentCheer = counter;
            resetVars();
            flash();
            updateHolidayCheer();
            prevRoomBattle = true;
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0) {
            flash();
            if (counter < 0)
                counter = 0;
            killThisCombat = true;
            grayscale = true;
            Wiz.att(new ChangeChristmasSpiritAction(-1));
            Wiz.atb(new RelicAboveCreatureAction(m, this));
        }
    }

    @Override
    public void onObtainCard(AbstractCard card) {
        if (card.color == AbstractCard.CardColor.CURSE) {
            if (ModHelper.isModEnabled("Hoarder")) {
                Wiz.atb(new ChangeChristmasSpiritAction(-3));
            } else
                Wiz.atb(new ChangeChristmasSpiritAction(-1));
        }
    }

    @Override
    public void onVictory() {
        if (!killThisCombat) {
            flash();
            CardCrawlGame.sound.play("GOLD_GAIN");
            Wiz.atb(new RelicAboveCreatureAction(adp(), this));
            adp().heal(counter + getBonusHealing());
            adp().gainGold(counter);
        }
    }

    private int getBonusHealing() {
        int bonus = 0;
        if (adp().hasPower(ChristmasCookiePower.POWER_ID))
            bonus += adp().getPower(ChristmasCookiePower.POWER_ID).amount;
        return bonus;
    }

    @Override
    public int changeNumberOfCardsInReward(int amount) {
        if (!killThisCombat && prevRoomBattle)
            amount += 1;
        return amount;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        resetVars();
    }

    @Override
    public void setCounter(int amount) {
        counter = amount;
        if (counter > 12)
            counter = 12;
        if (counter < 0)
            counter = 0;
    }

    private void resetVars() {
        killThisCombat = false;
        grayscale = false;
        prevRoomBattle = false;
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
                if (!monster.escaped && !monster.isDead && !monster.isEscaping) {
                    if (monster.hasPower(HolidayCheerPower.POWER_ID))
                        monster.getPower(HolidayCheerPower.POWER_ID).amount = currentCheer;
                    else
                        atb(new ApplyPowerAction(monster, monster, new HolidayCheerPower(monster, currentCheer)));
                }
            }
        }
    }
}*/
