package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class ChooChooPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(ChooChooPower.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public ChooChooPower(AbstractCreature owner, int num) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, num);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (owner.currentBlock > 0) {
            flash();
            addToBot(new SFXAction("ATTACK_HEAVY"));
            addToBot(new VFXAction(owner, new CleaveEffect(), 0.1F));
            addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(owner.currentBlock * amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];

    }

    @Override
    public AbstractPower makeCopy() {
        return new ChooChooPower(owner, amount);
    }
}
