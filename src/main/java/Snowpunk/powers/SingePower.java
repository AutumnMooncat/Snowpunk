package Snowpunk.powers;

import Snowpunk.powers.interfaces.MonsterOnPlayerEndTurnPower;
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

public class SingePower extends AbstractEasyPower implements MonsterOnPlayerEndTurnPower {
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
    public void atEndOfPlayerTurn() {
        if (!owner.hasPower(CharPower.POWER_ID))
            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
