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

public class ClockworkMod extends AbstractCardModifier implements XCostModifier {
    public static final String ID = makeID(ClockworkMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    private static ArrayList<TooltipInfo> GearTip, Tooltip;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/GearIcon.png");

    public ClockworkMod() {
        this(0);
    }

    public ClockworkMod(int amount) {
        priority = 1;
        this.amount += amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.CLOCKWORK);
        CardModifierManager.addModifier(card, new PrefixManager());
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
            if (clockworkMod.amount < 0)
                clockworkMod.amount = 0;
            return false;
        }

        return true;
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (GearTip == null) {
            GearTip = new ArrayList<>();
            GearTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
        }

        if (Tooltip == null)
            Tooltip = new ArrayList<>();
        if (amount > 0)
            return GearTip;
        return Tooltip;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if (group == Wiz.adp().hand)
            Wiz.atb(new ClockworkTickAction(this, card));
    }


    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 0)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }
/*
    @Override
    public void onRetained(AbstractCard card) {
        Wiz.atb(new ReduceCostAction(card));
    }*/

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ClockworkMod(amount);
    }

    @Override
    public int modifyX(AbstractCard abstractCard) {
        if (amount > 0)
            return amount;
        return 0;
    }
}