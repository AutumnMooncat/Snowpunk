package Snowpunk.cardmods;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.RemoveCardModifierAction;
import Snowpunk.patches.SnowballPatches;
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
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class CondensedMod extends AbstractCardModifier {
    public static final String ID = makeID(CondensedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    public boolean setFreeAndExhaust = false;
    private static ArrayList<TooltipInfo> CondenseTip;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/icons/Temp.png");

    public CondensedMod() {
        priority = 999;
        setFreeAndExhaust = false;
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return !checkEnoughEnergy(card) || super.canPlayCard(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!checkEnoughEnergy(card)) {
            card.freeToPlayOnce = true;
            action.exhaustCard = true;
        }
    }


    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    private boolean checkEnoughEnergy(AbstractCard card) {
        if (card.freeToPlayOnce || card.freeToPlay() || card.costForTurn == 0)
            return true;
        if (card.costForTurn <= EnergyPanel.getCurrentEnergy() + SnowballPatches.Snowballs.getTrueAmount())
            return true;
        return false;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (CondenseTip == null) {
            CondenseTip = new ArrayList<>();
            CondenseTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.COND), BaseMod.getKeywordDescription(KeywordManager.COND)));
        }
        return CondenseTip;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CondensedMod();
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(tex).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 0)
            ExtraIcons.icon(tex).render(card);
    }
}