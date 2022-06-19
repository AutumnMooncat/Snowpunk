package Snowpunk.powers;

import Snowpunk.powers.interfaces.OnUseSnowPower;
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

public class FrostbitePower extends AbstractEasyPower implements HealthBarRenderPower, OnUseSnowPower {
    public static String POWER_ID = makeID(FrostbitePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private final AbstractCreature source;

    private final Color hpBarColor = Color.SKY.cpy();

    public FrostbitePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, false, owner, amount);
        this.loadRegion("int");
        this.source = source;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }

    @Override
    public void onUseSnowball(int snow) {
        flashWithoutSound();
        Wiz.atb(new DamageAction(owner, new DamageInfo(source, amount*snow, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
