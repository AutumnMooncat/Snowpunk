package Snowpunk.powers;

import Snowpunk.damageMods.TempSingeMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class TooMuchPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(TooMuchPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public boolean triggered;

    public TooMuchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("attackBurn");
        triggered = false;
    }

    @Override
    public void atStartOfTurn() {
        triggered = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!triggered) {
            triggered = true;
            flash();
            AbstractMonster mo = AbstractDungeon.getRandomMonster();
            if (mo != null && mo.currentHealth > 0) {
                Wiz.atb(new ApplyPowerAction(mo, Wiz.adp(), new SingePower(mo, amount)));
            }
            CardTemperatureFields.addHeat(card, CardTemperatureFields.HOT);
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TooMuchPower(owner, amount);
    }
}
