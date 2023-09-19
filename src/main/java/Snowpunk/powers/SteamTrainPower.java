package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamTrainPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(SteamTrainPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public int blockMult, damageMult;
    private static int IDOffset;
    private static final Texture tex84 = TexLoader.getTexture("SnowpunkResources/images/powers/SteamTrain84.png");
    private static final Texture tex32 = TexLoader.getTexture("SnowpunkResources/images/powers/SteamTrain32.png");


    public SteamTrainPower(AbstractCreature owner, int amount, int blockMult, int damageMult) {
        super(POWER_ID + IDOffset, strings.NAME, PowerType.BUFF, true, owner, amount);
        IDOffset++;
        this.blockMult = blockMult;
        this.damageMult = damageMult;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        if (amount <= 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public float modifyBlock(float blockAmount) {
        if (blockAmount < 1)
            return blockAmount;
        return Math.max(blockAmount * blockMult, 0);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * damageMult;
        return damage;
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0] + damageMult + DESCRIPTIONS[3] + blockMult + DESCRIPTIONS[4];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + damageMult + DESCRIPTIONS[3] + blockMult + DESCRIPTIONS[4];
    }
}
