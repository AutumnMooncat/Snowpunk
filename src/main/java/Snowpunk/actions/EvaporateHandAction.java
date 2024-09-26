package Snowpunk.actions;

import Snowpunk.cards.interfaces.EvaporateHandCard;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class EvaporateHandAction extends AbstractGameAction {

    private final float startingDuration;

    private final boolean canPick;

    public static final String ID = makeID("Enhance");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    EvaporateHandCard card;

    public EvaporateHandAction(boolean canPick, EvaporateHandCard card) {
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.canPick = canPick;
        this.card = card;
    }

    public void update() {
        if (canPick) {
            if (duration == startingDuration) {
                if (AbstractDungeon.player.hand.size() == 0) {
                    isDone = true;
                    return;
                }
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 999, true, true);
                tickDuration();
                return;
            }

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                    Evaporate(c);

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            tickDuration();
        } else {
            if (AbstractDungeon.player.hand.size() == 0) {
                isDone = true;
                return;
            }
            int numCards = AbstractDungeon.player.hand.size();
            for (int i = 0; i < numCards; ++i) {
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        Evaporate(null);
                        isDone = true;
                    }
                });
            }
            isDone = true;
        }
    }

    void Evaporate(AbstractCard c) {
        if (c == null)
            c = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        EvaporatePanel.evaporatePile.addToTop(c);
        card.OnEvaporateCard(c);
        AbstractDungeon.player.hand.removeCard(c);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        for (AbstractPower pow : Wiz.adp().powers) {
            if (pow instanceof OnEvaporatePower) {
                ((OnEvaporatePower) pow).onEvaporate(c);
            }
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower pow : m.powers) {
                if (pow instanceof OnEvaporatePower) {
                    ((OnEvaporatePower) pow).onEvaporate(c);
                }
            }
        }
    }
}