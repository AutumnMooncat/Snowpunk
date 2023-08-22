package Snowpunk.cardmods;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.RemoveCardModifierAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

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

    /*
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
    }*/

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HatMod(amount);
    }


    //REGION TOP HAT PATCH
    @SpirePatch2(clz = CardModifierManager.class, method = "onRender")
    public static class RenderHook {
        @SpirePrefixPatch
        public static void preMods(AbstractCard card, SpriteBatch sb) {
            if (isOnScreen(card) && CardModifierManager.hasModifier(card, HatMod.ID) && ((HatMod) CardModifierManager.getModifiers(card, HatMod.ID).get(0)).amount > 0) {
                doElementRendering(card, sb);
            }
        }
    }

    public static boolean isOnScreen(AbstractCard card) {
        return (card.current_y >= -200.0F * Settings.scale && card.current_y <= Settings.HEIGHT + 200.0F * Settings.scale);
    }

    public static void doElementRendering(AbstractCard card, SpriteBatch sb) {
        ArrayList<Texture> elements = new ArrayList<>();
        elements.add(tex);
        int amount = 0;
        if (CardModifierManager.hasModifier(card, HatMod.ID))
            amount = ((HatMod) CardModifierManager.getModifiers(card, HatMod.ID).get(0)).amount;
        float spacing = tex.getWidth();
        if (!elements.isEmpty()) {
            sb.setColor(Color.WHITE.cpy());
            float dx = -(elements.size() - 1) * spacing / 2F;
            float dy = 230f;
            for (int i = 0; i < amount; i++) {
                sb.draw(tex, card.current_x + dx - tex.getWidth() / 2f, card.current_y + dy - tex.getHeight() / 2f + 20 * i,
                        tex.getWidth() / 2f - dx, tex.getHeight() / 2f - dy, tex.getWidth(), tex.getHeight(),
                        card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle,
                        0, 0, tex.getWidth(), tex.getHeight(), false, false);
            }
        }
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onSingleCardViewRender")
    public static class SCVRenderHook {
        @SpirePrefixPatch
        public static void preMods(SingleCardViewPopup screen, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(screen, SingleCardViewPopup.class, "card");
            if (CardModifierManager.hasModifier(card, HatMod.ID) && ((HatMod) CardModifierManager.getModifiers(card, HatMod.ID).get(0)).amount > 0)
                doElementRenderingSCV(card, sb);
        }
    }

    public static void doElementRenderingSCV(AbstractCard card, SpriteBatch sb) {
        ArrayList<Texture> elements = new ArrayList<>();
        elements.add(tex);
        int amount = 0;
        if (CardModifierManager.hasModifier(card, HatMod.ID))
            amount = ((HatMod) CardModifierManager.getModifiers(card, HatMod.ID).get(0)).amount;
        float spacingSCV = tex.getWidth();
        if (!elements.isEmpty()) {
            sb.setColor(Color.WHITE.cpy());
            float dx = -(elements.size() - 1) * spacingSCV / 2F;
            float dy = 450f;
            for (int i = 0; i < amount; i++) {
                sb.draw(tex, Settings.WIDTH / 2f + dx - tex.getWidth() / 2f, Settings.HEIGHT / 2f + dy - tex.getHeight() / 2f + i * 20,
                        tex.getWidth() / 2f - dx, tex.getHeight() / 2f - dy, tex.getWidth(), tex.getHeight(),
                        Settings.scale, Settings.scale, card.angle,
                        0, 0, tex.getWidth(), tex.getHeight(), false, false);
            }
        }
    }
    //ENDREGION
}