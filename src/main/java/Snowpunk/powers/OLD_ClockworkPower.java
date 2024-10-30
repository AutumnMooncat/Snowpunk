package Snowpunk.powers;

import Snowpunk.actions.ClockworkTickAction;
import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class OLD_ClockworkPower extends TwoAmountPower implements CloneablePowerInterface, NonStackablePower {
    public static String POWER_ID = makeID(OLD_ClockworkPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private int cardsThisTurn = 0;

    public OLD_ClockworkPower(AbstractCreature owner, int amount, int amount2) {
        this.ID = POWER_ID;
        isTurnBased = false;

        name = strings.NAME;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        this.type = PowerType.BUFF;
/*
        Texture normalTexture = TexLoader.getTexture(SnowpunkMod.modID + "Resources/images/powers/" + name.replace(" ","") + "32.png");
        Texture hiDefImage = TexLoader.getTexture(SnowpunkMod.modID + "Resources/images/powers/" + name.replace(" ","") + "84.png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }*/
        this.loadRegion("nirvana");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        cardsThisTurn = 0;
        flash();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.baseDamage > -1 || card.baseBlock > -1) {
            if (cardsThisTurn < amount2) {
                cardsThisTurn++;
                addToBot(new ClockworkTickAction(amount, card));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount2 == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new OLD_ClockworkPower(owner, amount, amount2);
    }
}
