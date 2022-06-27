package Snowpunk.patches;

import Snowpunk.cards.interfaces.MultiUpgradeCard;
import Snowpunk.util.UpgradeData;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.BranchingUpgradesPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;

public class MultiUpgradePatches {
    public static ArrayList<AbstractCard> cardList = new ArrayList<>();

    @SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
    public static class MultiSelectFields {
        public static SpireField<ArrayList<AbstractCard>> previewCards = new SpireField<>(ArrayList::new);
        public static SpireField<Boolean> waitingForUpgradeSelection = new SpireField<>(() -> false);
        public static SpireField<Integer> chosenIndex = new SpireField<>(() -> -1);
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class MultiUpgradeFields {
        public static SpireField<ArrayList<UpgradeData>> upgrades = new SpireField<>(ArrayList::new);
        public static SpireField<Integer> upgradeIndex = new SpireField<>(() -> -1);
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {String.class, String.class, String.class, int.class, String.class, AbstractCard.CardType.class, AbstractCard.CardColor.class, AbstractCard.CardRarity.class, AbstractCard.CardTarget.class, DamageInfo.DamageType.class})
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class})
    public static class AddUpgrades {
        @SpirePostfixPatch
        public static void plz(AbstractCard __instance) {
            if (__instance instanceof MultiUpgradeCard) {
                ((MultiUpgradeCard) __instance).addUpgrades();
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "canUpgrade")
    public static class AllowUpgrades {
        @SpirePrefixPatch
        public static SpireReturn<?> canUpgrade(AbstractCard __instance) {
            if (__instance instanceof MultiUpgradeCard) {
                return SpireReturn.Return(((MultiUpgradeCard) __instance).canPerformUpgrade(__instance));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
    public static class SaveMultiUpgrades {
        static int muxedUpgrades = 0;
        @SpireInsertPatch(rloc = 9, localvars = {"retVal"})
        public static void getMuxedUpgrades(String key, @ByRef int[] upgradeTime, int misc, AbstractCard retVal) {
            if (retVal instanceof MultiUpgradeCard) {
                muxedUpgrades = upgradeTime[0];
                upgradeTime[0] = 0;
            }
        }
        @SpireInsertPatch(rloc = 13, localvars = {"retVal"})
        public static void doMuxedUpgrades(String key, @ByRef int[] upgradeTime, int misc, AbstractCard retVal) {
            if (retVal instanceof MultiUpgradeCard) {
                for (int i = 0 ; i < 32 ; i++) {
                    if ((muxedUpgrades & (1 << i)) != 0) {
                        MultiUpgradeFields.upgradeIndex.set(retVal, i);
                        retVal.upgrade();
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class CopiesRetainMultiUpgrade {
        static int muxedUpgrades = 0;
        @SpireInsertPatch(rloc = 2, localvars = {"card"})
        public static void getUpgrades(AbstractCard __instance, AbstractCard card) {
            if (__instance instanceof MultiUpgradeCard) {
                muxedUpgrades = __instance.timesUpgraded;
                __instance.timesUpgraded = 0;
            }
        }

        @SpireInsertPatch(rloc = 6, localvars = {"card"})
        public static void doUpgrades(AbstractCard __instance, AbstractCard card) {
            if (__instance instanceof MultiUpgradeCard) {
                __instance.timesUpgraded = muxedUpgrades;
                for (int i = 0 ; i < 32 ; i++) {
                    if ((muxedUpgrades & (1 << i)) != 0) {
                        MultiUpgradeFields.upgradeIndex.set(card, i);
                        card.upgrade();
                    }
                }
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class GetMultiUpgrades {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(GridCardSelectScreen __instance) {
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();
            if (c instanceof MultiUpgradeCard) {
                MultiSelectFields.previewCards.get(__instance).clear();
                boolean skip = true;
                for (UpgradeData u : ((MultiUpgradeCard) c).getUpgrades(c)) {
                    //TODO this doubles up on the original upgrade card
                    if (u.canUpgrade(((MultiUpgradeCard) c).getUpgrades(c))) {
                        if (skip) {
                            skip = false;
                            continue;
                        }
                        AbstractCard copy = c.makeStatEquivalentCopy();
                        MultiUpgradeFields.upgradeIndex.set(copy, u.index);
                        copy.upgrade();
                        copy.displayUpgrades();
                        MultiUpgradeFields.upgradeIndex.set(copy, u.index); //Gets set back to -1 when completed

                        MultiSelectFields.previewCards.get(__instance).add(copy);
                    }

                }
                MultiSelectFields.waitingForUpgradeSelection.set(__instance, true);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "makeStatEquivalentCopy");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "cancelUpgrade")
    public static class CancelUpgrade {
        public static void Prefix(GridCardSelectScreen __instance) {
            MultiSelectFields.waitingForUpgradeSelection.set(__instance, false);
            MultiSelectFields.chosenIndex.set(__instance, -1);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "update")
    public static class SelectMultiUpgrade {
        public static void Postfix(AbstractCard __instance) {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.gridSelectScreen.forUpgrade && __instance.hb.hovered && InputHelper.justClickedLeft) {
                if (__instance instanceof MultiUpgradeCard) {
                    __instance.beginGlowing();
                    cardList.forEach((c) -> {
                        if (c != __instance) {
                            c.stopGlowing();
                        }

                    });
                }
                MultiSelectFields.chosenIndex.set(AbstractDungeon.gridSelectScreen, MultiUpgradeFields.upgradeIndex.get(__instance));
                MultiSelectFields.waitingForUpgradeSelection.set(AbstractDungeon.gridSelectScreen, false);
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class ConfirmUpgrade {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(GridCardSelectScreen __instance) {
            AbstractCard hoveredCard = BranchingUpgradesPatch.getHoveredCard();
            if (hoveredCard instanceof MultiUpgradeCard) {
                MultiUpgradeFields.upgradeIndex.set(hoveredCard, MultiSelectFields.chosenIndex.get(__instance));
                MultiSelectFields.chosenIndex.set(__instance, -1);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "closeCurrentScreen");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[found.length - 1]};
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "renderArrows")
    public static class RenderSplitArrows {
        @SpirePrefixPatch
        public static SpireReturn<?> downwardArrows(GridCardSelectScreen __instance, SpriteBatch sb, @ByRef float[] ___arrowTimer, @ByRef float[] ___arrowScale1, @ByRef float[] ___arrowScale2, @ByRef float[] ___arrowScale3) {
            if (__instance.forUpgrade && BranchingUpgradesPatch.getHoveredCard() instanceof MultiUpgradeCard) {
                float dx = 300F * Settings.scale;
                float x = Settings.WIDTH / 2.0F - 32F * Settings.scale;
                float y = Settings.HEIGHT * 0.75F - 50.0F * Settings.scale + 64.0F * Settings.scale;
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.UPGRADE_ARROW, x - dx, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale1[0] * Settings.scale, ___arrowScale1[0] * Settings.scale, -90.0F, 0, 0, 64, 64, false, false);
                sb.draw(ImageMaster.UPGRADE_ARROW, x + dx, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale1[0] * Settings.scale, ___arrowScale1[0] * Settings.scale, -90.0F, 0, 0, 64, 64, false, false);
                y -= 64.0F * Settings.scale;
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.UPGRADE_ARROW, x - dx, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale2[0] * Settings.scale, ___arrowScale2[0] * Settings.scale, -90.0F, 0, 0, 64, 64, false, false);
                sb.draw(ImageMaster.UPGRADE_ARROW, x + dx, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale2[0] * Settings.scale, ___arrowScale2[0] * Settings.scale, -90.0F, 0, 0, 64, 64, false, false);
                y -= 64.0F * Settings.scale;
                sb.draw(ImageMaster.UPGRADE_ARROW, x - dx, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale3[0] * Settings.scale, ___arrowScale3[0] * Settings.scale, -90.0F, 0, 0, 64, 64, false, false);
                sb.draw(ImageMaster.UPGRADE_ARROW, x + dx, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale3[0] * Settings.scale, ___arrowScale3[0] * Settings.scale, -90.0F, 0, 0, 64, 64, false, false);
                ___arrowTimer[0] += Gdx.graphics.getDeltaTime() * 2.0F;
                ___arrowScale1[0] = 0.8F + (MathUtils.cos(___arrowTimer[0]) + 1.0F) / 8.0F;
                ___arrowScale2[0] = 0.8F + (MathUtils.cos(___arrowTimer[0] - 0.8F) + 1.0F) / 8.0F;
                ___arrowScale3[0] = 0.8F + (MathUtils.cos(___arrowTimer[0] - 1.6F) + 1.0F) / 8.0F;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
        /*public static ExprEditor Instrument() {
            return new ExprEditor() {// 223
                private int count = 0;

                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(SpriteBatch.class.getName()) && m.getMethodName().equals("draw")) {// 228
                        if (this.count != 0) {// 229
                            m.replace("if (forUpgrade && hoveredCard instanceof " + BranchingUpgradesCard.class.getName() + ") {$10 = 45f;$3 += 64f * " + Settings.class.getName() + ".scale *" + this.count + ";$_ = $proceed($$);$10 = -45f;$3 -= 2 * 64f * " + Settings.class.getName() + ".scale *" + this.count + ";}$_ = $proceed($$);");// 230 232 235
                        }

                        ++this.count;// 239
                    }

                }// 241
            };
        }*/
    }

    @SpirePatch(clz = GridSelectConfirmButton.class, method = "render")
    public static class MultiUpgradeConfirm {
        public static SpireReturn<?> Prefix(GridSelectConfirmButton __instance, SpriteBatch sb) {
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();// 209
            return MultiSelectFields.waitingForUpgradeSelection.get(AbstractDungeon.gridSelectScreen) && c instanceof MultiUpgradeCard ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "render")
    public static class RenderBranchingUpgrade {
        public static ExprEditor Instrument() {
            return new ExprEditor() {// 137
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("renderArrows")) {// 141
                        m.replace("$_ = $proceed($$);if (" + MultiUpgradePatches.RenderBranchingUpgrade.class.getName() + ".Do(this, sb).isPresent()) {return;}");
                    }
                }// 147
            };
        }

        public static SpireReturn<?> Do(GridCardSelectScreen __instance, SpriteBatch sb) {
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();
            if (__instance.forUpgrade && c instanceof MultiUpgradeCard) {
                cardList.clear();
                //TODO reorganize this
                c.current_x = (float)Settings.WIDTH * 0.5F;
                c.current_y = (float)Settings.HEIGHT * 0.75F - 50.0F * Settings.scale;
                c.target_x = (float)Settings.WIDTH * 0.5F;
                c.target_y = (float)Settings.HEIGHT * 0.75F - 50.0F * Settings.scale;
                c.render(sb);
                c.updateHoverLogic();
                c.hb.resize(0.0F, 0.0F);

                cardList.add(__instance.upgradePreviewCard);
                cardList.addAll(MultiSelectFields.previewCards.get(__instance));
                int lineNum = 0;
                int count = 0;
                for (AbstractCard card : cardList) {
                    int cardsThisRow = Math.min(5, cardList.size() - (5 * lineNum));
                    int startX = (int) (Settings.WIDTH / 2.0F - (cardsThisRow - 1) * 150F * Settings.scale);
                    if (card.hb.hovered) {
                        card.drawScale = 1.0F;
                    } else {
                        card.drawScale = 0.9F;
                    }
                    card.current_x = startX + 300F * Settings.scale * count;
                    card.current_y = Settings.HEIGHT / 4.0F + (420.0F * lineNum * Settings.scale);
                    card.target_x = card.current_x;
                    card.target_y = card.current_y;
                    card.render(sb);
                    card.updateHoverLogic();
                    card.renderCardTip(sb);

                    count++;
                    if (count == 5) {
                        count = 0;
                        lineNum++;
                    }
                }

                /*
                if (__instance.upgradePreviewCard.hb.hovered) {
                    __instance.upgradePreviewCard.drawScale = 1.0F;
                } else {
                    __instance.upgradePreviewCard.drawScale = 0.9F;
                }
                __instance.upgradePreviewCard.current_x = (float)Settings.WIDTH * 0.63F;
                __instance.upgradePreviewCard.current_y = (float)Settings.HEIGHT / 4.0F + 50.0F * Settings.scale;
                __instance.upgradePreviewCard.target_x = (float)Settings.WIDTH * 0.63F;
                __instance.upgradePreviewCard.target_y = (float)Settings.HEIGHT / 4.0F + 50.0F * Settings.scale;
                __instance.upgradePreviewCard.render(sb);
                __instance.upgradePreviewCard.updateHoverLogic();
                __instance.upgradePreviewCard.renderCardTip(sb);
                cardList.add(__instance.upgradePreviewCard);
                int i = 0;
                for (AbstractCard preview : MultiSelectFields.previewCards.get(__instance)) {
                    i++;
                    if (preview.hb.hovered) {
                        preview.drawScale = 1.0F;
                    } else {
                        preview.drawScale = 0.9F;
                    }

                    preview.current_x = (float)Settings.WIDTH * 0.63F + 300.0F * i * Settings.scale;
                    preview.current_y = (float)Settings.HEIGHT / 2.0F;
                    preview.target_x = (float)Settings.WIDTH * 0.63F + 300.0F * i * Settings.scale;
                    preview.target_y = (float)Settings.HEIGHT / 2.0F;
                    preview.render(sb);
                    preview.updateHoverLogic();
                    preview.renderCardTip(sb);
                    cardList.add(preview);
                }*/

                if (__instance.forUpgrade || __instance.forTransform || __instance.forPurge || __instance.isJustForConfirming || __instance.anyNumber) {// 188
                    __instance.confirmButton.render(sb);// 189
                }

                //TODO what is this for
                CardGroup targetGroup = ReflectionHacks.getPrivate(__instance, GridCardSelectScreen.class, "targetGroup");// 191
                String tipMsg = ReflectionHacks.getPrivate(__instance, GridCardSelectScreen.class, "tipMsg");// 192
                if (!__instance.isJustForConfirming || targetGroup.size() > 5) {// 193
                    FontHelper.renderDeckViewTip(sb, tipMsg, 96.0F * Settings.scale, Settings.CREAM_COLOR);// 194
                }
                return SpireReturn.Return(null);// 196
            } else {
                return SpireReturn.Continue();// 198
            }
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class UpdatePreviewCards {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(GridCardSelectScreen __instance) {
            for (AbstractCard c : MultiSelectFields.previewCards.get(__instance)) {
                if (c != null) {
                    c.update();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "update");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
            }
        }
    }

    //TODO may not be needed if we defaults to first upgrade available
    @SpirePatch(clz = GridCardSelectScreen.class, method = "update")
    public static class ForceNormalUpgrade {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(GridCardSelectScreen __instance) {
            if (__instance.upgradePreviewCard instanceof MultiUpgradeCard) {
                MultiUpgradeFields.upgradeIndex.set(__instance.upgradePreviewCard, -1);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
