package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class GumdropPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(GumdropPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public GumdropPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("armor");
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner != this.owner && amount > 0 && damageAmount > 0) {
            if (info.owner.hasPower(ChillPower.POWER_ID)) {
                ((ChillPower) info.owner.getPower(ChillPower.POWER_ID)).keepThisTurn += amount;
                addToTop(new ApplyPowerAction(info.owner, owner, new ChillPower(info.owner, amount), amount));
            } else {
                addToTop(new ApplyPowerAction(info.owner, owner, new ChillPower(info.owner, amount, amount), amount));
            }
            flash();
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
