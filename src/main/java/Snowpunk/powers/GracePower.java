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
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class GracePower extends AbstractEasyPower {
    public static String POWER_ID = makeID(GracePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static int IDOffset = 0;

    int threshold;

    public GracePower(AbstractCreature owner, int amount, int threshold) {
        super(POWER_ID + IDOffset, strings.NAME, PowerType.BUFF, false, owner, amount);
        IDOffset++;
        this.loadRegion("nirvana");
        this.threshold = threshold;
        updateDescription();
    }


    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount >= threshold) {
            addToTop(new ReducePowerAction(owner, owner, this, 1));
            return 0;
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0] + threshold + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + threshold + DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GracePower(owner, amount, threshold);
    }
}
