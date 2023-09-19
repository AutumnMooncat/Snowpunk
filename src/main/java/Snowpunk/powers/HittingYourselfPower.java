package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class HittingYourselfPower extends AbstractEasyPower implements HealthBarRenderPower {
    public static String POWER_ID = makeID(HittingYourselfPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private final AbstractCreature source;


    public HittingYourselfPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, false, owner, amount);
        this.loadRegion("int");
        this.source = source;
    }

    @Override
    public void atStartOfTurn() {
        if (owner instanceof AbstractMonster && ((AbstractMonster) owner).getIntentBaseDmg() >= 0)
            Wiz.atb(new DamageAction(owner, new DamageInfo(Wiz.adp(), amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount() {
        if (owner instanceof AbstractMonster && ((AbstractMonster) owner).getIntentBaseDmg() >= 0)
            return amount;
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
