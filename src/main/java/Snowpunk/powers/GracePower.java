package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class GracePower extends AbstractEasyPower {
    public static String POWER_ID = makeID(GracePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public GracePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }


    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount >= 10) {
            addToTop(new ReducePowerAction(owner, owner, this, 1));
            return 0;
        }
        return damageAmount;
    }

    /*
        @Override
        public void onVictory() {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.currentHealth > 0 && amount > p.currentHealth)
                p.heal(amount - p.currentHealth);
        }

        @Override
        public boolean onPlayerDeath(AbstractPlayer player, DamageInfo damageInfo) {
            player.heal(amount);
            Wiz.att(new RemoveSpecificPowerAction(player, player, this));
            return false;
        }
    */
    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

}
