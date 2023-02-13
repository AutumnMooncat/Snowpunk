package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class ScrapShieldPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ScrapShieldPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public ScrapShieldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, true, owner, amount);
        this.loadRegion("closeUp");
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            this.flash();
            this.addToTop(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new OLD_SparePartsPower(Wiz.adp(), amount)));
        }
        return damageAmount;
    }

    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }// 55

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
