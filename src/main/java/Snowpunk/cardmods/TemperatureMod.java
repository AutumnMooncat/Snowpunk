package Snowpunk.cardmods;

import Snowpunk.actions.*;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.patches.LoopcastField;
import Snowpunk.powers.ColdDrawPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.HotEnergyPower;
import Snowpunk.ui.EvaporateTutorial;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.ColdSnowflakeEffect;
import Snowpunk.vfx.VictorySnowflakeEffects;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.devcommands.draw.Draw;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static Snowpunk.SnowpunkMod.*;
import static Snowpunk.SnowpunkMod.modConfig;
import static java.lang.Math.abs;

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID(TemperatureMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/icons/Temp.png");
    private static final Texture hot = TexLoader.getTexture(modID + "Resources/images/icons/Hot.png");
    private static final Texture cold = TexLoader.getTexture(modID + "Resources/images/icons/Cold.png");

    public static final int COLD = -1, HOT = 1;
    int heatMod = 0;

    public TemperatureMod() {
        this.priority = -2;
    }

    public TemperatureMod(int heatMod) {
        this.priority = -2;
        this.heatMod = heatMod;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (heatMod != -0)
            CardTemperatureFields.addHeat(card, heatMod);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (CardTemperatureFields.getCardHeat(card) >= HOT && !CardModifierManager.hasModifier(card, FlaminMod.ID)) {
            String out = KeywordManager.HOT.replace(modID.toLowerCase(), "");
            out = out.replace(":", "");
            out = out.substring(0, 1).toUpperCase() + out.substring(1);
            return modID.toLowerCase() + ":" + out + ". NL " + rawDescription;
        }

        if (CardTemperatureFields.getCardHeat(card) <= COLD) {
            String out = KeywordManager.COLD.replace(modID.toLowerCase(), "");
            out = out.replace(":", "");
            out = out.substring(0, 1).toUpperCase() + out.substring(1);
            return modID.toLowerCase() + ":" + out + ". NL " + rawDescription;
        }

        return rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int heat = CardTemperatureFields.getCardHeat(card);

        if (heat >= HOT) {
            EvaporatePanelPatches.EvaporateField.evaporate.set(card, true);
        }

        if (heat <= COLD)
            Wiz.att(new DrawCardAction(Math.abs(heat)));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            card.initializeDescription();
            return false;
        }
        return true;
    }

    @Override
    public void onUpdate(AbstractCard card) {
        super.onUpdate(card);
        int heat = CardTemperatureFields.getCardHeat(card);
        if (AbstractDungeon.player != null)
            if (heat <= COLD && MathUtils.random(20) < -COLD && (AbstractDungeon.player.hand.contains(card) || AbstractDungeon.player.limbo.contains(card)))
                AbstractDungeon.topLevelEffectsQueue.add(new ColdSnowflakeEffect(card));
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat >= HOT)
            ExtraIcons.icon(hot).text(String.valueOf(heat)).render(card);
        if (heat <= COLD) {
            ExtraIcons.icon(cold).text(String.valueOf(Math.abs(heat))).render(card);
        }
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat >= HOT)
            ExtraIcons.icon(hot).text(String.valueOf(heat)).render(card);
        if (heat <= COLD)
            ExtraIcons.icon(cold).text(String.valueOf(Math.abs(heat))).render(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TemperatureMod();
    }


}
