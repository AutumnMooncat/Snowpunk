package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class SingePower extends AbstractEasyPower implements HealthBarRenderPower {
    public static String POWER_ID = makeID(SingePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private final AbstractCreature source;

    public int keepThisTurn;
    private final Color hpBarColor = new Color(240 / 256f, 180 / 256f, 24 / 256f, 1);

    public SingePower(AbstractCreature owner, AbstractCreature source, int amount) {
        this(owner, source, amount, 0);
    }

    public SingePower(AbstractCreature owner, AbstractCreature source, int amount, int keepThisTurn) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        this.loadRegion("flameBarrier");
        this.source = source;
        priority = -1;
        this.keepThisTurn = keepThisTurn;
    }

    @Override
    public void atStartOfTurn() {
        int embers = 0;
        if (AbstractDungeon.player.hasPower(BurningEnginePower.POWER_ID))
            embers = AbstractDungeon.player.getPower(BurningEnginePower.POWER_ID).amount;
        if (embers > 0)
            Wiz.atb(new DamageAction(owner, new DamageInfo(source, embers, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

//            if (keepThisTurn <= 0)
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
//            else {
//                Wiz.atb(new ReducePowerAction(owner, owner, this, amount - keepThisTurn));
//                keepThisTurn = 0;
//            }
    }

//    @Override
//    public void atEndOfRound() {
//        int embers = 0;
//        if (AbstractDungeon.player.hasPower(BurningEmbersPower.POWER_ID))
//            embers = AbstractDungeon.player.getPower(BurningEmbersPower.POWER_ID).amount;
//        if (embers > 0)
//            Wiz.atb(new DamageAction(owner, new DamageInfo(source, embers, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
//
//        if (keepThisTurn <= 0)
//            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
//        else {
//            Wiz.atb(new ReducePowerAction(owner, owner, this, amount - keepThisTurn));
//            keepThisTurn = 0;
//        }
//    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL)
            return damage + amount;
        return damage;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS &&
                info.owner != null && info.owner != owner) {
            flash();
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount() {
        int embers = 0;
        if (AbstractDungeon.player.hasPower(BurningEnginePower.POWER_ID))
            embers = AbstractDungeon.player.getPower(BurningEnginePower.POWER_ID).amount;
        return embers;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }
}
