package Snowpunk.ui;

import Snowpunk.SnowpunkMod;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.vfx.ExhaustPileParticle;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class EvaporatePanel extends AbstractPanel {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(SnowpunkMod.makeID("EvaporatePanel"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final float SHOW_X = 198.0F * Settings.scale;
    private static final float SHOW_Y = 300.0F * Settings.scale;
    private static final float HIDE_X = -480.0F * Settings.scale;
    private static final float HIDE_Y = 284.0F * Settings.scale;
    private static final float TIP_X = 50.0F * Settings.scale;
    private static final float TIP_Y = 475.0F * Settings.scale;
    private static final float COUNT_CIRCLE_W = 128.0F * Settings.scale;
    public static float fontScale;
    public static float energyVfxTimer;
    public static Hitbox hb = null;

    public static CardGroup evaporatePile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public EvaporatePanel() {
        super(SHOW_X, SHOW_Y, HIDE_X, HIDE_Y, null, false);
        hb = new Hitbox(0.0F, 0.0F, 100.0F * Settings.scale, 100.0F * Settings.scale);
    }

    @Override
    public void updatePositions() {
        super.updatePositions();
        if (!this.isHidden && evaporatePile.size() > 0) {
            hb.update();
            this.updateVfx();
        }

        if (hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT || AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD && AbstractDungeon.overlayMenu.combatPanelsShown)) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
            if (InputHelper.justClickedLeft) {
                hb.clickStarted = true;
            }
        }
        EvaporatePanel.evaporatePile.size();
        if ((hb.clicked) && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
            hb.clicked = false;
            hb.hovered = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
        } else {
            if ((hb.clicked) && AbstractDungeon.overlayMenu.combatPanelsShown && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead() && !AbstractDungeon.player.isDead && !evaporatePile.isEmpty()) {
                hb.clicked = false;
                hb.hovered = false;
                if (AbstractDungeon.isScreenUp) {
                    if (AbstractDungeon.previousScreen == null) {
                        AbstractDungeon.previousScreen = AbstractDungeon.screen;
                    }
                } else {
                    AbstractDungeon.previousScreen = null;
                }

                this.openExhaustPile();
            }

        }
    }

    public static void DelayedEvaporate(AbstractCard card) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                EvaporatePanel.Evaporate(card);
                isDone = true;
            }
        });
    }

    public static void Evaporate(AbstractCard card) {
        EvaporatePanel.evaporatePile.group.add(getShuffleInPosition(card), card);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(card));
        for (AbstractPower pow : Wiz.adp().powers) {
            if (pow instanceof OnEvaporatePower) {
                ((OnEvaporatePower) pow).onEvaporate(card);
            }
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower pow : m.powers) {
                if (pow instanceof OnEvaporatePower) {
                    ((OnEvaporatePower) pow).onEvaporate(card);
                }
            }
        }
        if (Wiz.adp().hand.contains(card))
            Wiz.adp().hand.removeCard(card);
        if (Wiz.adp().discardPile.contains(card))
            Wiz.adp().discardPile.removeCard(card);
        if (Wiz.adp().drawPile.contains(card))
            Wiz.adp().drawPile.removeCard(card);
        if (Wiz.adp().limbo.contains(card))
            Wiz.adp().limbo.removeCard(card);
    }

    private static int getShuffleInPosition(AbstractCard card) {
//        if(EvaporatePanel.evaporatePile.group.size() == 0)
//            return 0;
//
//        if(EvaporatePanel.evaporatePile.group.size() == 1)
//        {
//            AbstractCard c = EvaporatePanel.evaporatePile.group.get(0);
//            if(getComparableCost(card) > getComparableCost(c))
//                return 1;
//            return 0;
//        }
//
//        for (int i = 1; i < EvaporatePanel.evaporatePile.group.size(); i++) {
//            AbstractCard c = EvaporatePanel.evaporatePile.group.get(i);
//            AbstractCard prevCard = EvaporatePanel.evaporatePile.group.get(i - 1);
//            if(getComparableCost(card) < getComparableCost(c) && getComparableCost(card) >= getComparableCost(prevCard))
//                return i;
//        }
        return AbstractDungeon.shuffleRng.random(0, EvaporatePanel.evaporatePile.group.size());
    }

    private static int getComparableCost(AbstractCard card) {
        if (card.cost >= 0) return card.cost;
        if (card.cost == -1) return 999;
        return 9999;
    }

    private void openExhaustPile() {
        if (AbstractDungeon.player.hoveredCard != null) {
            AbstractDungeon.player.releaseCard();
        }

        AbstractDungeon.dynamicBanner.hide();
        EvaporatePanelPatches.RenderEvaporateInsteadField.renderEvaporate.set(AbstractDungeon.exhaustPileViewScreen, true);
        AbstractDungeon.exhaustPileViewScreen.open();
        hb.hovered = false;
        InputHelper.justClickedLeft = false;
    }

    private void updateVfx() {
        energyVfxTimer -= Gdx.graphics.getDeltaTime();
        if (energyVfxTimer <= 0.0F && !Settings.hideLowerElements) {
            AbstractDungeon.effectList.add(new ExhaustPileParticle(this.current_x, this.current_y));
            energyVfxTimer = 0.05F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (!evaporatePile.isEmpty()) {
            hb.move(this.current_x, this.current_y);
            String msg = Integer.toString(evaporatePile.size());
            sb.setColor(Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            sb.draw(ImageMaster.DECK_COUNT_CIRCLE, this.current_x - COUNT_CIRCLE_W / 2.0F, this.current_y - COUNT_CIRCLE_W / 2.0F, COUNT_CIRCLE_W, COUNT_CIRCLE_W);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, this.current_x, this.current_y + 2.0F * Settings.scale, Settings.CREAM_COLOR);

            hb.render(sb);
            if (hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                TipHelper.renderGenericTip(TIP_X, TIP_Y, TEXT[0], TEXT[1]);
            }
        }
    }
}
