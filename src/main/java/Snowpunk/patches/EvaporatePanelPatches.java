package Snowpunk.patches;

import Snowpunk.actions.CondenseAction;
import Snowpunk.powers.PyromaniacPower;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class EvaporatePanelPatches {
    @SpirePatch(clz = OverlayMenu.class, method = SpirePatch.CLASS)
    public static class EvaporatePanelField {
        public static SpireField<EvaporatePanel> panel = new SpireField<>(EvaporatePanel::new);
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "update")
    public static class UpdatePanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void update(OverlayMenu __instance) {
            EvaporatePanelField.panel.get(__instance).updatePositions();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "updatePositions");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "showCombatPanels")
    public static class ShowPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void show(OverlayMenu __instance) {
            EvaporatePanelField.panel.get(__instance).show();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "show");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "hideCombatPanels")
    public static class HidePanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void hide(OverlayMenu __instance) {
            EvaporatePanelField.panel.get(__instance).hide();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "hide");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "render")
    public static class RenderPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void render(OverlayMenu __instance, SpriteBatch sb) {
            EvaporatePanelField.panel.get(__instance).render(sb);
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "render");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class UpdatePile {
        @SpirePostfixPatch
        public static void update(AbstractPlayer __instance) {
            EvaporatePanel.evaporatePile.update();
        }
    }
/*
    @SpirePatch2(clz = AbstractPlayer.class, method = "draw", paramtypez = {int.class})
    public static class CheckDraw {
        @SpirePrefixPatch
        public static void draw(AbstractPlayer __instance, int ___numCards) {
            if(AbstractDungeon.overlayMenu.endTurnButton.enabled)
            {
                for(int i = 0; i < ___numCards; ++i)
                {
                    if(EvaporatePanel.evaporatePile.group.size() > 0)
                    {
                        AbstractCard evaporatedCard = EvaporatePanel.evaporatePile.group.get(AbstractDungeon.cardRandomRng.random(EvaporatePanel.evaporatePile.group.size() - 1));
                        AbstractDungeon.player.drawPile.addToTop(evaporatedCard);
                        EvaporatePanel.evaporatePile.group.remove(evaporatedCard);
                        CardTemperatureFields.reduceTemp(evaporatedCard);


                        evaporatedCard.unhover();
                        evaporatedCard.unfadeOut();
                        evaporatedCard.lighten(true);
                        evaporatedCard.fadingOut = false;

                        AbstractGameEffect e = null;
                        for (AbstractGameEffect effect : AbstractDungeon.effectList) {
                            if (effect instanceof ExhaustCardEffect) {
                                AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                                if (c == evaporatedCard)
                                    e = effect;
                            }
                        }
                        if(e != null)
                            AbstractDungeon.effectList.remove(e);
                    }
                }
            }
        }
    }*/

    @SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class ClearPile {
        @SpirePrefixPatch
        public static void clear(AbstractPlayer __instance) {
            EvaporatePanel.evaporatePile.clear();
        }
    }

    //Moving the card

    public static boolean shouldEvaporate(AbstractCard card) {
        /*if (card.hasTag(CustomTags.VENT)) {
            return false;
        }*/
        return EvaporateField.evaporate.get(card);
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class EvaporateField {
        public static SpireField<Boolean> evaporate = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = UseCardAction.class, method = "update")
    public static class MoveToEvaporatePile {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> yeet(UseCardAction __instance, AbstractCard ___targetCard) {
            //if (!__instance.exhaustCard && shouldEvaporate(___targetCard)) {
            if (shouldEvaporate(___targetCard)) {
                if (AbstractDungeon.player.hoveredCard == ___targetCard) {
                    AbstractDungeon.player.releaseCard();
                }
                AbstractDungeon.actionManager.removeFromQueue(___targetCard);
                ___targetCard.unhover();
                ___targetCard.untip();
                ___targetCard.stopGlowing();
                if (AbstractDungeon.player.hasPower(PyromaniacPower.POWER_ID)) {
                    //AbstractDungeon.player.exhaustPile.addToTop(___targetCard);
                    __instance.exhaustCard = true;
                    return SpireReturn.Continue();
                } else {
                    __instance.exhaustCard = false;
                    EvaporatePanel.evaporatePile.addToTop(___targetCard);
                    Wiz.adp().hand.group.remove(___targetCard);
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(___targetCard));
                    for (AbstractPower pow : Wiz.adp().powers) {
                        if (pow instanceof OnEvaporatePower) {
                            ((OnEvaporatePower) pow).onEvaporate(___targetCard);
                        }
                    }
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        for (AbstractPower pow : m.powers) {
                            if (pow instanceof OnEvaporatePower) {
                                ((OnEvaporatePower) pow).onEvaporate(___targetCard);
                            }
                        }
                    }
                }
                ___targetCard.exhaustOnUseOnce = false;
                AbstractDungeon.player.onCardDrawOrDiscard();
                EvaporateField.evaporate.set(___targetCard, false);
                ___targetCard.dontTriggerOnUseCard = false;
                Wiz.atb(new HandCheckAction());
                if (___targetCard.hasTag(CustomTags.VENT)) {
                    Wiz.atb(new CondenseAction(___targetCard));
                    AbstractGameEffect e = null;
                    for (AbstractGameEffect effect : AbstractDungeon.effectList) {
                        if (effect instanceof ExhaustCardEffect) {
                            AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                            if (c == ___targetCard)
                                e = effect;
                        }
                    }
                    if (e != null)
                        AbstractDungeon.effectList.remove(e);
                }

                ReflectionHacks.RMethod tick = ReflectionHacks.privateMethod(AbstractGameAction.class, "tickDuration");
                tick.invoke(__instance);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    //TODO jank below

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = SpirePatch.CLASS)
    public static class RenderEvaporateInsteadField {
        public static SpireField<Boolean> renderEvaporate = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = ExhaustPanel.class, method = "openExhaustPile")
    public static class ResetFlag {
        @SpirePrefixPatch
        public static void reset(ExhaustPanel __instance) {
            EvaporatePanelPatches.RenderEvaporateInsteadField.renderEvaporate.set(AbstractDungeon.exhaustPileViewScreen, false);
        }
    }

    @SpirePatch2(clz = ExhaustPileViewScreen.class, method = "open")
    public static class GrabEvaporatedCards {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(CardGroup.class.getName()) && f.getFieldName().equals("group")) {// 386
                        f.replace("$_ = Snowpunk.patches.EvaporatePanelPatches.GrabEvaporatedCards.getCards(this, $proceed($$));");
                    }
                }
            };
        }

        public static ArrayList<AbstractCard> getCards(ExhaustPileViewScreen screen, ArrayList<AbstractCard> cards) {
            if (RenderEvaporateInsteadField.renderEvaporate.get(screen)) {
                return EvaporatePanel.evaporatePile.group;
            }
            return cards;
        }
    }

    @SpirePatch2(clz = ExhaustPileViewScreen.class, method = "render")
    public static class ChangeMessage {
        public static ExprEditor Instrument() {
            return new ExprEditor() {

                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderDeckViewTip")) {// 386
                        m.replace("$2 = Snowpunk.patches.EvaporatePanelPatches.ChangeMessage.change(this, $2); $_ = $proceed($$);");
                    }
                }
            };
        }

        public static String change(ExhaustPileViewScreen screen, String msg) {
            if (RenderEvaporateInsteadField.renderEvaporate.get(screen)) {
                return EvaporatePanel.TEXT[2];
            }
            return msg;
        }
    }

    @SpirePatch2(clz = DrawCardAction.class, method = "update")
    public static class PreventReshuffle {
        @SpireInsertPatch(
                rloc = 17,
                localvars = {"deckSize"}
        )
        public static void update(DrawCardAction __instance, @ByRef int[] deckSize) {
            if (AbstractDungeon.overlayMenu.endTurnButton.enabled && EvaporatePanel.evaporatePile.group.size() > 0)
                deckSize[0] += EvaporatePanel.evaporatePile.group.size();
        }
    }

    @SpirePatch2(clz = DrawCardAction.class, method = "update")
    public static class DrawFromEvaporateBeforeDrawPile {
        @SpireInsertPatch(
                rloc = 69
        )
        public static void update(DrawCardAction __instance) {
            if (AbstractDungeon.overlayMenu.endTurnButton.enabled && EvaporatePanel.evaporatePile.size() > 0) {
                AbstractCard evaporatedCard = EvaporatePanel.evaporatePile.group.get(AbstractDungeon.cardRandomRng.random(EvaporatePanel.evaporatePile.group.size() - 1));
                AbstractDungeon.player.drawPile.addToTop(evaporatedCard);
                EvaporatePanel.evaporatePile.group.remove(evaporatedCard);
                if (CardTemperatureFields.getCardHeat(evaporatedCard) > 0)
                    CardTemperatureFields.reduceTemp(evaporatedCard);

                evaporatedCard.applyPowers();
                evaporatedCard.initializeDescription();
                evaporatedCard.unhover();
                evaporatedCard.unfadeOut();
                evaporatedCard.lighten(true);
                evaporatedCard.fadingOut = false;

                AbstractGameEffect e = null;
                for (AbstractGameEffect effect : AbstractDungeon.effectList) {
                    if (effect instanceof ExhaustCardEffect) {
                        AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                        if (c == evaporatedCard)
                            e = effect;
                    }
                }
                if (e != null)
                    AbstractDungeon.effectList.remove(e);
                /*if (__instance.amount == 0)
                    ReflectionHacks.privateMethod(DrawCardAction.class, "endActionWithFollowUp").invoke(__instance);
                return;*/
            }
        }
    }

    @SpirePatch2(clz = GetAllInBattleInstances.class, method = "get")
    public static class AddEvaporateToGetAllInBattleInstances {
        @SpireInsertPatch(
                rloc = 23,
                localvars = {"cards"}
        )
        public static void get(UUID uuid, HashSet<AbstractCard> cards) {
            for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
                if (c.uuid == uuid)
                    cards.add(c);
            }
        }
    }
}
