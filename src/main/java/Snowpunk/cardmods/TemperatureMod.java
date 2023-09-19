package Snowpunk.cardmods;

import Snowpunk.actions.*;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.patches.LoopcastField;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
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

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;
import static java.lang.Math.abs;

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID(TemperatureMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/icons/Temp.png");
    private static final Texture hot = TexLoader.getTexture(modID + "Resources/images/icons/Hot.png");
    private static final Texture cold = TexLoader.getTexture(modID + "Resources/images/icons/Cold.png");

    public static final int COLD = -1, HOT = 1;

    public TemperatureMod() {
        this.priority = -2;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (CardTemperatureFields.getCardHeat(card) == HOT)
            return modID.toLowerCase() + ":" + BaseMod.getKeywordProper(KeywordManager.HOT) + ". NL " + rawDescription;

        if (CardTemperatureFields.getCardHeat(card) == COLD)
            return modID.toLowerCase() + ":" + BaseMod.getKeywordProper(KeywordManager.COLD) + ". NL " + rawDescription;

        return rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int heat = CardTemperatureFields.getCardHeat(card);

        if (heat >= HOT)
            EvaporatePanelPatches.EvaporateField.evaporate.set(card, true);

        if (heat <= COLD)
            Wiz.att(new CondenseAction(abs(heat)));
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        if (!card.isEthereal && CardTemperatureFields.getCardHeat(card) <= COLD)
            card.retain = true;
        return false;
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
    public void onRender(AbstractCard card, SpriteBatch sb) {
        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat >= HOT)
            ExtraIcons.icon(hot).render(card);
        if (heat <= COLD)
            ExtraIcons.icon(cold).render(card);

        //.text(String.valueOf(heat))
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        int heat = CardTemperatureFields.getCardHeat(card);
        if (heat >= HOT)
            ExtraIcons.icon(hot).render(card);
        if (heat <= COLD)
            ExtraIcons.icon(cold).render(card);
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
