package Snowpunk.patches;

import Snowpunk.cards.interfaces.MultiUpgradeCard;
import Snowpunk.shaders.Grayscale;
import Snowpunk.shaders.Greenify;
import Snowpunk.util.CardGraph;
import Snowpunk.util.CardGraphEdge;
import Snowpunk.util.UpgradeData;
import Snowpunk.util.CardVertex;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.BranchingUpgradesPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.AbstractList;
import java.util.ArrayList;

public class MultiUpgradePatches {
    public static ArrayList<AbstractCard> cardList = new ArrayList<>();
    public static ArrayList<AbstractCard> takenList = new ArrayList<>();
    public static ArrayList<AbstractCard> lockedList = new ArrayList<>();
    public static CardGraph cardGraph = new CardGraph();
    public static float grabX, grabY;
    public static float deltaX, deltaY;
    public static float minX, minY;
    public static float maxX, maxY;
    public static boolean allowX, allowY;
    public static boolean dragging;
    public static float renderScale;
    private static final float X_PAD = 400F * Settings.scale;
    private static final float Y_PAD = 220F * Settings.scale;
    private static final float LINE_SPACING = 20F * Settings.scale;

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
            resetScrollState();
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();
            if (c instanceof MultiUpgradeCard) {
                //Prep the cards into the array
                MultiSelectFields.previewCards.get(__instance).clear();
                takenList.clear();
                lockedList.clear();
                cardGraph.clear();
                CardVertex root = new CardVertex(c, -1);
                root.move(-1, 0);
                cardGraph.addVertex(root);
                for (UpgradeData u : ((MultiUpgradeCard) c).getUpgrades(c)) {
                    AbstractCard copy;// = c.makeStatEquivalentCopy();
                    if (u.applied) {
                        copy = c.makeCopy();
                    } else {
                        copy = c.makeStatEquivalentCopy();
                    }
                    prepUpgradePreview(copy, u);
                    if (u.upgradeName != null && !u.upgradeName.isEmpty()) {
                        copy.name = u.upgradeName;
                        ReflectionHacks.privateMethod(AbstractCard.class, "initializeTitle").invoke(copy);
                    }
                    if (u.upgradeDescription != null && !u.upgradeDescription.isEmpty()) {
                        copy.rawDescription = u.upgradeDescription;
                        copy.initializeDescription();
                    }
                    /*MultiUpgradeFields.upgradeIndex.set(copy, u.index);
                    copy.upgrade();
                    copy.displayUpgrades();*/
                    MultiUpgradeFields.upgradeIndex.set(copy, u.index); //Gets set back to -1 when completed, so we need to set it again
                    if (u.alias != null) {
                        AbstractCard alias = u.alias.makeStatEquivalentCopy();
                        MultiUpgradeFields.upgradeIndex.set(alias, u.index);
                        alias.cardsToPreview = copy;
                        MultiSelectFields.previewCards.get(__instance).add(alias);
                    } else {
                        MultiSelectFields.previewCards.get(__instance).add(copy);
                    }

                    if (u.applied) {
                        takenList.add(copy);
                    } else if (!u.canUpgrade(((MultiUpgradeCard) c).getUpgrades(c))) {
                        lockedList.add(copy);
                    }
                    CardVertex v = new CardVertex(copy, u.index);
                    cardGraph.addVertex(v);
                    if (u.dependencies.isEmpty()) {
                        cardGraph.addDependence(v, root);
                    } else {
                        for (int i : u.dependencies) {
                            //Dependency directed graphs
                            cardGraph.addDependence(v, cardGraph.vertices.get(i+1));
                        }
                    }

                    /*if (u.canUpgrade(((MultiUpgradeCard) c).getUpgrades(c))) {
                        AbstractCard copy = c.makeStatEquivalentCopy();
                        MultiUpgradeFields.upgradeIndex.set(copy, u.index);
                        copy.upgrade();
                        copy.displayUpgrades();
                        MultiUpgradeFields.upgradeIndex.set(copy, u.index); //Gets set back to -1 when completed
                        if (u.alias != null) {
                            AbstractCard alias = u.alias.makeStatEquivalentCopy();
                            MultiUpgradeFields.upgradeIndex.set(alias, u.index);
                            alias.cardsToPreview = copy;
                            MultiSelectFields.previewCards.get(__instance).add(alias);
                        } else {
                            MultiSelectFields.previewCards.get(__instance).add(copy);
                        }
                    }*/
                }
                MultiSelectFields.waitingForUpgradeSelection.set(__instance, true);

                //Set the starting positions for the cards
                /*c.current_x = (float)Settings.WIDTH * 0.5F;
                c.current_y = (float)Settings.HEIGHT * 0.75F - 50.0F * Settings.scale;
                int lineNum = 0;
                int count = 0;
                for (AbstractCard card : MultiSelectFields.previewCards.get(__instance)) {
                    int cardsThisRow = Math.min(5, MultiSelectFields.previewCards.get(__instance).size() - (5 * lineNum));
                    int startX = (int) (Settings.WIDTH / 2.0F - (cardsThisRow - 1) * 150F * Settings.scale);
                    card.current_x = startX + 300F * Settings.scale * count;
                    card.current_y = Settings.HEIGHT / 4.0F - (420.0F * lineNum * Settings.scale);
                    count++;

                    if (count == 5) {
                        count = 0;
                        lineNum++;
                    }
                }*/

                c.current_x = (float)Settings.WIDTH * 1/3F;
                c.current_y = (float)Settings.HEIGHT / 2F;

                //Balance all cards in the X direction
                for (CardVertex v : cardGraph.vertices) {
                    for (CardVertex dependency : v.parents) {
                        if (dependency.x >= v.x) {
                            v.move(dependency.x + 1, v.y);
                        }
                        v.card.current_x = Settings.WIDTH * 2/3F + (v.x * X_PAD);
                    }
                }

                /*//Balance all cards in the Y direction around their respective parents
                for (CardVertex v : cardGraph.vertices) {
                    int yIndex = v.children.size() - 1;
                    for (CardVertex dependent : v.children) {
                        dependent.move(dependent.x, v.y + yIndex);
                        yIndex -= 2;
                    }
                }*/

                for (int i = 0 ; i <= cardGraph.depth() ; i++) {
                    int finalI = i;
                    final int[] yIndex = {(int) cardGraph.vertices.stream().filter(v -> v.x == finalI).count() - 1};
                    cardGraph.vertices.stream().filter(v -> v.x == finalI).forEach(v -> {
                        v.move(v.x, yIndex[0]);
                        v.card.current_y = Settings.HEIGHT / 2F + (v.y * Y_PAD);
                        yIndex[0] -= 2;
                    });
                }

                //Define the scroll limits
                float left, right, up, down;
                left = right = c.current_x;
                up = down = c.current_y;
                for (AbstractCard card : MultiSelectFields.previewCards.get(__instance)) {
                    if (card.current_x < left) {
                        left = card.current_x;
                    } else if (card.current_x > right) {
                        right = card.current_x;
                    }
                    if (card.current_y < down) {
                        down = card.current_y;
                    } else if (card.current_y > up) {
                        up = card.current_x;
                    }
                }

                //Add some padding
                left -= 200F;
                right += 200F;
                up += 260F;
                down -= 260F;

                if (left < 0) {
                    //minX = left - 200F * Settings.scale;
                    maxX = -left + 200F * Settings.scale;
                    allowX = true;
                }
                if (right > Settings.WIDTH) {
                    //maxX = right - Settings.WIDTH + 200F * Settings.scale;
                    minX = Settings.WIDTH - right - 200F * Settings.scale;
                    allowX = true;
                }
                if (down < 0) {
                    maxY = -down + 260F * Settings.scale;
                    allowY = true;
                }
                if (up > Settings.HEIGHT) {
                    minY = Settings.HEIGHT - up - 260F * Settings.scale;
                    allowY = true;
                }
                renderScale = 1f;
            }
        }

        private static void prepUpgradePreview(AbstractCard card, UpgradeData u) {
            for (int i : u.dependencies) {
                UpgradeData dep = ((MultiUpgradeCard)card).getUpgrades(card).get(i);
                if (!dep.applied) {
                    prepUpgradePreview(card, dep);
                }
            }
            MultiUpgradeFields.upgradeIndex.set(card, u.index);
            card.upgrade();
            card.displayUpgrades();
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
            resetScrollState();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "update")
    public static class SelectMultiUpgrade {
        public static void Postfix(AbstractCard __instance) {
            AbstractCard hovered = BranchingUpgradesPatch.getHoveredCard();
            if (__instance != hovered && hovered instanceof MultiUpgradeCard && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.gridSelectScreen.forUpgrade && __instance.hb.hovered && InputHelper.justClickedLeft) {
                if (cardList.contains(__instance) && !takenList.contains(__instance) && !lockedList.contains(__instance)) {
                    __instance.beginGlowing();
                    cardList.forEach((c) -> {
                        if (c != __instance) {
                            c.stopGlowing();
                        }

                    });

                    MultiSelectFields.chosenIndex.set(AbstractDungeon.gridSelectScreen, MultiUpgradeFields.upgradeIndex.get(__instance));
                    MultiSelectFields.waitingForUpgradeSelection.set(AbstractDungeon.gridSelectScreen, false);
                }
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
            resetScrollState();
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
            AbstractCard card = BranchingUpgradesPatch.getHoveredCard();
            if (__instance.forUpgrade && card instanceof MultiUpgradeCard) {
                //float x = (float)Settings.WIDTH / 2.0F - 73.0F * Settings.scale - 32.0F;
                sb.setColor(Color.WHITE);
                //sb.draw(ImageMaster.UPGRADE_ARROW, card.current_x + Settings.WIDTH / 6F * renderScale - 32.0F - 64F * Settings.scale * renderScale, card.current_y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale1[0] * Settings.scale * renderScale, ___arrowScale1[0] * Settings.scale * renderScale, 0.0F, 0, 0, 64, 64, false, false);
                //sb.draw(ImageMaster.UPGRADE_ARROW, card.current_x + Settings.WIDTH / 6F * renderScale - 32.0F, card.current_y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale2[0] * Settings.scale * renderScale, ___arrowScale2[0] * Settings.scale * renderScale, 0.0F, 0, 0, 64, 64, false, false);
                //sb.draw(ImageMaster.UPGRADE_ARROW, card.current_x + Settings.WIDTH / 6F * renderScale - 32.0F + 64F * Settings.scale * renderScale, card.current_y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, ___arrowScale3[0] * Settings.scale * renderScale, ___arrowScale3[0] * Settings.scale * renderScale, 0.0F, 0, 0, 64, 64, false, false);
                ___arrowTimer[0] += Gdx.graphics.getDeltaTime() * 2.0F;
                ___arrowScale1[0] = 0.8F + (MathUtils.cos(___arrowTimer[0]) + 1.0F) / 8.0F;
                ___arrowScale2[0] = 0.8F + (MathUtils.cos(___arrowTimer[0] - 0.8F) + 1.0F) / 8.0F;
                ___arrowScale3[0] = 0.8F + (MathUtils.cos(___arrowTimer[0] - 1.6F) + 1.0F) / 8.0F;

                AbstractCard hovered = null;
                for (CardVertex v : cardGraph.vertices) {
                    if (v.card.hb.hovered) {
                        hovered = v.card;
                        break;
                    }
                }
                for (CardVertex v : cardGraph.vertices) {
                    for (CardVertex child : v.children) {
                        if (hovered != null) {
                            if (child.card == hovered) {
                                if (v.index == -1 || takenList.contains(v.card)) {
                                    sb.setColor(Color.GREEN);
                                } else if (!lockedList.contains(v.card)) {
                                    sb.setColor(Color.ORANGE);
                                } else {
                                    sb.setColor(Color.RED);
                                }
                            } else {
                                sb.setColor(Settings.QUARTER_TRANSPARENT_WHITE_COLOR);
                            }
                        }

                        Vector2 vec2 = (new Vector2((child.card.current_x),child.card.current_y)).sub(new Vector2((v.card.current_x), v.card.current_y));
                        float length = vec2.len();
                        int mod = 0;
                        for (float i = 0; i < length; i += LINE_SPACING) {
                            vec2.clamp(length - i, length - i);
                            sb.draw(ImageMaster.MAP_DOT_1, (v.card.current_x) + vec2.x - 8.0F, v.card.current_y + vec2.y - 8.0F, 8.0F, 8.0F, 16.0F, 16.0F, Settings.scale, Settings.scale, (new Vector2((v.card.current_x) - (child.card.current_x), v.card.current_y - child.card.current_y)).nor().angle() + 90.0F, 0, 0, 16, 16, false, false);
                            //sb.draw(ImageMaster.UPGRADE_ARROW, (v.card.current_x) + vec2.x - 32.0F, v.card.current_y + vec2.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, (new Vector2((v.card.current_x) - (child.card.current_x), v.card.current_y - child.card.current_y)).nor().angle(), 0, 0, 64, 64, true, false);
                            /*if (mod == 0) {
                                sb.draw(ImageMaster.UPGRADE_ARROW, (v.card.current_x) + vec2.x - 32.0F, v.card.current_y + vec2.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * renderScale * ___arrowScale1[0], Settings.scale * renderScale * ___arrowScale1[0], (new Vector2((v.card.current_x) - (child.card.current_x), v.card.current_y - child.card.current_y)).nor().angle(), 0, 0, 64, 64, true, false);
                            } else if (mod == 1) {
                                sb.draw(ImageMaster.UPGRADE_ARROW, (v.card.current_x) + vec2.x - 32.0F, v.card.current_y + vec2.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * renderScale * ___arrowScale2[0], Settings.scale * renderScale * ___arrowScale2[0], (new Vector2((v.card.current_x) - (child.card.current_x), v.card.current_y - child.card.current_y)).nor().angle(), 0, 0, 64, 64, true, false);
                            } else {
                                sb.draw(ImageMaster.UPGRADE_ARROW, (v.card.current_x) + vec2.x - 32.0F, v.card.current_y + vec2.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * renderScale * ___arrowScale3[0], Settings.scale * renderScale * ___arrowScale3[0], (new Vector2((v.card.current_x) - (child.card.current_x), v.card.current_y - child.card.current_y)).nor().angle(), 0, 0, 64, 64, true, false);
                            }
                            mod++;
                            mod = mod % 3;*/
                        }
                        sb.setColor(Color.WHITE);
                    }
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "updateScrolling")
    public static class ScrollShenanigans {
        public static void Prefix(GridCardSelectScreen __instance) {
            int x = InputHelper.mX;
            int y = InputHelper.mY;
            if (!dragging) {
                if (InputHelper.justClickedLeft) {
                    dragging = true;
                    grabX = x - deltaX;
                    grabY = y - deltaY;
                }
            } else if (InputHelper.isMouseDown) {
                if (allowX) {
                    deltaX = x - grabX;
                }
                if (allowY) {
                    deltaY = y - grabY;
                }

            } else {
                dragging = false;
            }

            if (deltaX < minX) {
                deltaX = MathHelper.scrollSnapLerpSpeed(deltaX, minX);
            } else if (deltaX > maxX) {
                deltaX = MathHelper.scrollSnapLerpSpeed(deltaX, maxX);
            }
            if (deltaY < minY) {
                deltaY = MathHelper.scrollSnapLerpSpeed(deltaY, minY);
            } else if (deltaY > maxY) {
                deltaY = MathHelper.scrollSnapLerpSpeed(deltaY, maxY);
            }
            if (InputHelper.scrolledDown && renderScale > 0.5f) {
                renderScale -= 0.1f;
            } else if (InputHelper.scrolledUp && renderScale < 1f) {
                renderScale += 0.1f;
            }
        }
    }

    public static void resetScrollState() {
        deltaX = 0;
        deltaY = 0;
        minX = 0;
        maxX = 0;
        minY = 0;
        maxY = 0;
        allowX = false;
        allowY = false;
        renderScale = 1f;
    }

    @SpirePatch(clz = GridSelectConfirmButton.class, method = "render")
    public static class MultiUpgradeConfirmRender {
        public static SpireReturn<?> Prefix(GridSelectConfirmButton __instance, SpriteBatch sb) {
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();
            return MultiSelectFields.waitingForUpgradeSelection.get(AbstractDungeon.gridSelectScreen) && c instanceof MultiUpgradeCard ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = GridSelectConfirmButton.class, method = "update")
    public static class MultiUpgradeConfirmUpdate {
        public static SpireReturn<?> Prefix(GridSelectConfirmButton __instance) {
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();
            return MultiSelectFields.waitingForUpgradeSelection.get(AbstractDungeon.gridSelectScreen) && c instanceof MultiUpgradeCard ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "hover")
    public static class StopJitteringPlz {
        @SpirePrefixPatch
        public static SpireReturn<?> plz(AbstractCard __instance, @ByRef boolean[] ___hovered) {
            AbstractCard hovered = BranchingUpgradesPatch.getHoveredCard();
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.gridSelectScreen.forUpgrade && AbstractDungeon.gridSelectScreen.confirmScreenUp && BranchingUpgradesPatch.getHoveredCard() instanceof MultiUpgradeCard) {
                if (!___hovered[0]) {
                    ___hovered[0] = true;
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "render")
    public static class HideGhostCard {
        @SpireInstrumentPatch
        public static ExprEditor plz() {
            return new ExprEditor() {
                int count = 0;
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (count == 0 && m.getMethodName().equals("render") && m.getClassName().equals(AbstractCard.class.getName())) {
                        count++;
                        m.replace("if (Snowpunk.patches.MultiUpgradePatches.HideGhostCard.renderTheCard()) {$_ = $proceed($$);}");
                    }
                }
            };
        }

        @SpireInstrumentPatch
        public static ExprEditor plz2() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("renderHoverShadow") && m.getClassName().equals(AbstractCard.class.getName())) {
                        m.replace("if (Snowpunk.patches.MultiUpgradePatches.HideGhostCard.renderTheCard()) {$_ = $proceed($$);}");
                    }
                }
            };
        }

        public static boolean renderTheCard() {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.gridSelectScreen.forUpgrade && AbstractDungeon.gridSelectScreen.confirmScreenUp && BranchingUpgradesPatch.getHoveredCard() instanceof MultiUpgradeCard) {
                return false;
            }
            return true;
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "render")
    public static class RenderMultiUpgrade {
        public static ExprEditor Instrument() {
            return new ExprEditor() {// 137
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("renderArrows")) {
                        m.replace("$_ = $proceed($$);if (" + RenderMultiUpgrade.class.getName() + ".Do(this, sb).isPresent()) {return;}");
                    }
                }// 147
            };
        }

        public static SpireReturn<?> Do(GridCardSelectScreen __instance, SpriteBatch sb) {
            AbstractCard c = BranchingUpgradesPatch.getHoveredCard();
            if (__instance.forUpgrade && c instanceof MultiUpgradeCard) {
                float dx = deltaX;
                float dy = deltaY;
                cardList.clear();
                //TODO reorganize this
                c.target_x = (float)Settings.WIDTH * 1/3F + dx;
                c.target_y = (float)Settings.HEIGHT / 2F + dy;
                c.drawScale = renderScale;
                //c.target_x = (float)Settings.WIDTH * 0.5F + dx;
                //c.target_y = (float)Settings.HEIGHT * 0.75F - 50.0F * Settings.scale + dy;
                c.render(sb);
                c.updateHoverLogic();

                cardList.addAll(MultiSelectFields.previewCards.get(__instance));
                int lineNum = 0;
                int count = 0;
                for (CardVertex v : cardGraph.vertices) {
                    if (v.x != -1) {
                        if (v.card.hb.hovered) {
                            v.card.drawScale = renderScale;
                        } else {
                            v.card.drawScale = 0.9F * renderScale;
                        }
                        v.card.target_x = c.target_x + (Settings.WIDTH / 3F * renderScale) + (v.x * X_PAD * renderScale);
                        v.card.target_y = c.target_y + (v.y * Y_PAD * renderScale);

                        if (lockedList.contains(v.card)) {
                            sb.end();
                            sb.setShader(Grayscale.program);
                            sb.begin();
                            //card.setLocked();
                        } else if (takenList.contains(v.card)) {
                            sb.end();
                            sb.setShader(Greenify.program);
                            sb.begin();
                        }
                        v.card.render(sb);
                        ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
                        v.card.updateHoverLogic();
                        v.card.renderCardTip(sb);
                    }
                }
                /*for (AbstractCard card : cardList) {
                    int cardsThisRow = Math.min(5, cardList.size() - (5 * lineNum));
                    int startX = (int) (Settings.WIDTH / 2.0F - (cardsThisRow - 1) * 150F * Settings.scale);
                    if (card.hb.hovered) {
                        card.drawScale = 1.0F;
                    } else {
                        card.drawScale = 0.9F;
                    }
                    card.target_x = startX + 300F * Settings.scale * count + dx;
                    card.target_y = Settings.HEIGHT / 4.0F - (420.0F * lineNum * Settings.scale) + dy;

                    if (lockedList.contains(card)) {
                        sb.end();
                        sb.setShader(Grayscale.program);
                        sb.begin();
                        //card.setLocked();
                    }
                    card.render(sb);
                    ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
                    card.updateHoverLogic();
                    card.renderCardTip(sb);

                    count++;
                    if (count == 5) {
                        count = 0;
                        lineNum++;
                    }
                }*/

                if (__instance.forUpgrade || __instance.forTransform || __instance.forPurge || __instance.isJustForConfirming || __instance.anyNumber) {
                    __instance.confirmButton.render(sb);
                }

                CardGroup targetGroup = ReflectionHacks.getPrivate(__instance, GridCardSelectScreen.class, "targetGroup");
                String tipMsg = ReflectionHacks.getPrivate(__instance, GridCardSelectScreen.class, "tipMsg");
                if (!__instance.isJustForConfirming || targetGroup.size() > 5) {
                    FontHelper.renderDeckViewTip(sb, tipMsg, 96.0F * Settings.scale, Settings.CREAM_COLOR);
                    //FontHelper.renderDeckViewTip(sb, tipMsg, Settings.HEIGHT / 2F - 25F * Settings.scale, Settings.CREAM_COLOR);
                }
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
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
