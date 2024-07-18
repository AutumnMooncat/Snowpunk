package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Mr10BelowPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(Mr10BelowPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    boolean played = false;

    int amount2;

    public Mr10BelowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        amount2 = 1;
        this.loadRegion("int");
        played = false;
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        amount2 = 1;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2++;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        played = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && CardTemperatureFields.canModTemp(card, -1) &&
                CardTemperatureFields.getCardHeat(card) < 0 && !played &&
                !owner.hasPower(SteamPower.POWER_ID) && !owner.hasPower(FireburstPower.POWER_ID) &&
                !owner.hasPower(FireballPower.POWER_ID)) {
            flash();
            Wiz.atb(new GainBlockAction(owner, amount));
            CardTemperatureFields.addHeat(card, -amount2);
            played = true;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Mr10BelowPower(owner, amount);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (amount2 > 0) {
            Color color = Color.GREEN.cpy();
            color.a = c.a;
            c = color;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }
    }
}
