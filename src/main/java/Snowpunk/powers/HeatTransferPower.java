package Snowpunk.powers;

import Snowpunk.damageMods.BurnDamage;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatTransferPower extends AbstractEasyPower implements DamageModApplyingPower {
    public static String POWER_ID = makeID(HeatTransferPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public HeatTransferPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("firebreathing");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAddedDamageModsToDamageInfo(DamageInfo info, Object instigator) {
        flash();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        if (o instanceof AbstractCard && ((AbstractCard) o).type == AbstractCard.CardType.ATTACK) {
            return CardTemperatureFields.getExpectedCardHeatWhenPlayed((AbstractCard) o) > 0;
        }
        return false;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new BurnDamage(amount));
    }
}
