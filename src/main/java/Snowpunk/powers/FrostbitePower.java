package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class FrostbitePower extends AbstractEasyPower implements HealthBarRenderPower {
    public static String POWER_ID = makeID(FrostbitePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private final AbstractCreature source;

    private final Color hpBarColor = Color.SKY.cpy();

    public FrostbitePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        this.loadRegion("int");
        this.source = source;
    }

    @Override
    public void atEndOfRound() {
        flash();
        Wiz.atb(new DamageAction(owner, new DamageInfo(source, amount*getSnowballs(), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount() {
        return amount*getSnowballs();
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }

    private int getSnowballs() {
        if (Wiz.adp().hasPower(SnowballPower.POWER_ID)) {
            return Wiz.adp().getPower(SnowballPower.POWER_ID).amount;
        }
        return 0;
    }
}
