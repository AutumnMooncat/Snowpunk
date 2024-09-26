package Snowpunk.powers;

import Snowpunk.powers.interfaces.MonsterOnPlayerEndTurnPower;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnDrawPileShufflePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class SingePower extends AbstractEasyPower implements OnEvaporatePower, HealthBarRenderPower {
    public static String POWER_ID = makeID(SingePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private final Color hpBarColor = new Color(240 / 256f, 180 / 256f, 24 / 256f, 1);

    public SingePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.DEBUFF, true, owner, amount);
        this.loadRegion("flameBarrier");
        priority = -1;
//        if(!AbstractDungeon.player.hasPower(SingeHelperPower.POWER_ID))
//            Wiz.atb(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new SingeHelperPower(AbstractDungeon.player, 1), 0));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SingePower(owner, amount);
    }

    @Override
    public void onEvaporate(AbstractCard card) {
//        AbstractGameEffect fire = new FlashAtkImgEffect(owner.hb.cX, owner.hb.cY, AbstractGameAction.AttackEffect.FIRE);
//        //fire.duration = fire.startingDuration = .05f;
//        AbstractDungeon.effectList.add(fire);
//        Wiz.atb(new WaitAction(.05f));
//        Wiz.atb(new AbstractGameAction() {
//            @Override
//            public void update() {
//                owner.damage(new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.HP_LOSS));
//                isDone = true;
//            }
//        });
        Wiz.atb(new LoseHPAction(owner, Wiz.adp(), amount, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }
}
