package Snowpunk.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class NextTurnPowerPower extends AbstractEasyPower {
    public static String TEXT_ID = makeID("NextTurnPowerPower");
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(TEXT_ID);
    private AbstractPower powerToGain;
        /*
    private static Texture arrow48 = TexLoader.getTexture("SnowpunkResources/images/ui/arrow48.png");
    private static Texture arrow128 = TexLoader.getTexture("SnowpunkResources/images/ui/arrow128.png");
    public static HashMap<String, TextureAtlas.AtlasRegion> bufferHashMap48 = new HashMap<>();
    public static HashMap<String, TextureAtlas.AtlasRegion> bufferHashMap128 = new HashMap<>();
    */

    public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
        super(TEXT_ID + powerToGrant.ID, strings.NAME + powerToGrant.name, powerToGrant.type, false, owner, powerToGrant.amount);
        this.img = powerToGrant.img;
        this.region48 = powerToGrant.region48;
        this.region128 = powerToGrant.region128;
        this.powerToGain = powerToGrant;
        updateDescription();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.GREEN.cpy());
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        powerToGain.amount += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, powerToGain, powerToGain.amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToGain == null) {
            description = "???";
        } else {
            description = strings.DESCRIPTIONS[0] + powerToGain.amount + strings.DESCRIPTIONS[1] + powerToGain.name + strings.DESCRIPTIONS[2];
        }
    }
}
