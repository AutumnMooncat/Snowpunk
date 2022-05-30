package Snowpunk.powers;

import Snowpunk.util.SteamEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class EngineTempPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(EngineTempPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public EngineTempPower(AbstractCreature owner) {
        super(POWER_ID, strings.NAME, NeutralPowertypePatch.NEUTRAL, false, owner, -1);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, String.valueOf(SteamEngine.heat), x, y, this.fontScale, c);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        int heat = SteamEngine.heat;
        if (heat == SteamEngine.FREEZING) {
            description += DESCRIPTIONS[1];
        } else if (heat <= SteamEngine.COLD) {
            description += DESCRIPTIONS[2];
        } else if (heat < SteamEngine.HOT) {
            description += DESCRIPTIONS[3];
        } else if (heat < SteamEngine.OVERHEATED) {
            description += DESCRIPTIONS[4];
        } else {
            description += DESCRIPTIONS[5];
        }
    }

}
