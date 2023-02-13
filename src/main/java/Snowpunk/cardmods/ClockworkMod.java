package Snowpunk.cardmods;

import Snowpunk.actions.ClockworkTickAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import basemod.interfaces.XCostModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class ClockworkMod extends AbstractCardModifier {
    public static final String ID = makeID(ClockworkMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;

    public ClockworkMod() {
        this(1);
    }

    public ClockworkMod(int amount) {
        priority = 1;
        this.amount += amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.CLOCKWORK);
        CardModifierManager.addModifier(card, new PrefixManager());
        CardModifierManager.addModifier(card, new GearMod(0));
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(CustomTags.CLOCKWORK);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            ClockworkMod clockworkMod = (ClockworkMod) CardModifierManager.getModifiers(card, ID).get(0);
            clockworkMod.amount += amount;
            if (clockworkMod.amount < 1)
                clockworkMod.amount = 1;
            return false;
        }

        return true;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (group == Wiz.adp().hand)
            Wiz.atb(new ClockworkTickAction(amount, card));
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ClockworkMod(amount);
    }
}