package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class JingleBellsPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(JingleBellsPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private int amount2;
    public static Color greenColor2 = Color.GREEN.cpy();
    private static int idOffset;

    public JingleBellsPower(AbstractCreature owner, int amount, int amount2) {
        super(POWER_ID + idOffset, strings.NAME, PowerType.BUFF, false, owner, amount);
        idOffset++;
        this.amount2 = amount2;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        Wiz.atb(new GainBlockAction(owner, amount));
        amount2--;
        if (amount2 <= 0)
            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount2 == 1)
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (amount2 > 0) {
            greenColor2.a = c.a;
            c = greenColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x, y + 15.0F * Settings.scale, fontScale, c);
        }
    }
}
