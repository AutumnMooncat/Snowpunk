package Snowpunk.patches;

import Snowpunk.cardmods.MkMod;
import Snowpunk.util.TexLoader;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;
import static Snowpunk.patches.SnowballPatches.Snowballs.amount;

public class SnowballUIPatches {
    private static Texture texture = TexLoader.getTexture("SnowpunkResources/images/char/mainChar/orb/SnowballSmol.png");
    public static final String ID = makeID(SnowballUIPatches.class.getSimpleName());
    public static Hitbox tipHitbox = new Hitbox(0.0F, 0.0F, 80.0F * Settings.scale, 80.0F * Settings.scale);// 35
    //public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(modID + ":Snowballs");
    public static final String[] TEXT = uiString.TEXT;

    @SpirePatch(clz = EnergyPanel.class, method = "render")
    public static class EnergyPanel_Render {
        @SpirePrefixPatch
        public static void prefix(EnergyPanel __instance, SpriteBatch sb) {

            tipHitbox.update();
            if (tipHitbox.hovered && !AbstractDungeon.isScreenUp) {
                AbstractDungeon.overlayMenu.hoveredTip = true;
            }

            SnowballUIPatches.Render(sb);
        }
    }

    public static void Render(SpriteBatch spriteBatch) {
        float x = 280.0F * Settings.xScale;
        float y = 245.0F * Settings.yScale;
        tipHitbox.move(x, y);
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && (amount > 0 || SnowballPatches.Snowballs.getPerTurn() > 0)) {
            spriteBatch.setColor(1, 1, 1, 1);
            spriteBatch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
            String display = String.valueOf(amount);
            if (SnowballPatches.Snowballs.getPerTurn() > 0)
                display += "/" + SnowballPatches.Snowballs.getPerTurn();

            float tempScale = FontHelper.energyNumFontBlue.getScaleX();
            FontHelper.energyNumFontBlue.getData().setScale(tempScale * .75f);
            FontHelper.renderFontCentered(spriteBatch, FontHelper.energyNumFontBlue, display, x, y);
            FontHelper.energyNumFontBlue.getData().setScale(tempScale);

            if (tipHitbox.hovered)
                TipHelper.renderGenericTip(60.0F * Settings.scale, 450.0F * Settings.scale, TEXT[0], TEXT[1]);
        }
    }
}
