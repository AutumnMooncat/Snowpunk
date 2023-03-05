package Snowpunk.cardmods;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.RemoveCardModifierAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
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
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class HatMod extends AbstractCardModifier {
    public static final String ID = makeID(HatMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    private static ArrayList<TooltipInfo> HatTip;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/icons/Hat.png");

    public HatMod() {
        this(1);
    }

    public HatMod(int amount) {
        priority = 1;
        this.amount += amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            HatMod hatMod = (HatMod) CardModifierManager.getModifiers(card, ID).get(0);
            hatMod.amount += amount;
            if (hatMod.amount < 0)
                hatMod.amount = 0;
            return false;
        }
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (amount > 0) {
            if (Wiz.adp().hand.contains(card))
                Wiz.adp().hand.removeCard(card);
            for (int i = 0; i < amount; i++) {
                if (Wiz.adp().hand.size() > 0)
                    Wiz.atb(new ApplyCardModifierAction(Wiz.adp().hand.getRandomCard(true), new HatMod()));
            }
            Wiz.atb(new RemoveCardModifierAction(card, this));
            Wiz.atb(new DrawCardAction(amount));
        }
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (HatTip == null) {
            HatTip = new ArrayList<>();
            HatTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.HAT), BaseMod.getKeywordDescription(KeywordManager.HAT)));
        }
        return HatTip;
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 1)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
        else
            ExtraIcons.icon(tex).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 1)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
        else
            ExtraIcons.icon(tex).render(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HatMod(amount);
    }
}