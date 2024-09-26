package Snowpunk.powers;

import Snowpunk.actions.DrawEvaporatedCardsAction;
import Snowpunk.actions.EvaporateCardInHandAction;
import Snowpunk.actions.ExhumeEvaporatedCardAction;
import Snowpunk.actions.MoveFromOnePileToAnotherAction;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;
import static Snowpunk.util.Wiz.atb;

public class ShovelCoalPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ShovelCoalPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public ShovelCoalPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }
/*
    @Override
    public void atStartOfTurn() {
        numExhausted = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (numExhausted < amount) {
            numExhausted++;
            Wiz.atb(new MoveFromOnePileToAnotherAction(card, Wiz.adp().exhaustPile, EvaporatePanel.evaporatePile));
            for (AbstractPower power : Wiz.adp().powers) {
                if (power instanceof OnEvaporatePower)
                    ((OnEvaporatePower) power).onEvaporate(card);
            }
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (monster != null && !monster.isDeadOrEscaped()) {
                    for (AbstractPower power : monster.powers) {
                        if (power instanceof OnEvaporatePower)
                            ((OnEvaporatePower) power).onEvaporate(card);
                    }
                }
            }
            flash();
        }
        super.onExhaust(card);
    }*/
/*
    @Override
    public void atStartOfTurnPostDraw() {
        if (Wiz.adp().exhaustPile.group.size() + Wiz.adp().discardPile.group.size() > 0) {
            List<AbstractCard> cards = new ArrayList<>();
            cards.addAll(Wiz.adp().exhaustPile.group);
            cards.addAll(Wiz.adp().discardPile.group);

            for (int i = 0; i < amount; i++) {
                AbstractCard card = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
                card.unhover();
                card.untip();
                card.stopGlowing();

                if (Wiz.adp().exhaustPile.group.contains(card)) {
                    Wiz.adp().exhaustPile.group.remove(card);
                    EvaporatePanel.evaporatePile.addToTop(card);
                }
                if (adp().discardPile.group.contains(card)) {
                    Wiz.adp().discardPile.group.remove(card);
                    EvaporatePanel.evaporatePile.addToTop(card);
                }

                for (AbstractPower pow : adp().powers) {
                    if (pow instanceof OnEvaporatePower) {
                        ((OnEvaporatePower) pow).onEvaporate(card);
                    }
                }
                for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    for (AbstractPower pow : monster.powers) {
                        if (pow instanceof OnEvaporatePower) {
                            ((OnEvaporatePower) pow).onEvaporate(card);
                        }
                    }
                }
                flash();
            }
        }
    }
*/

    @Override
    public void atStartOfTurnPostDraw() {
        if (EvaporatePanel.evaporatePile.size() > 0) {
            flash();
            Wiz.atb(new DrawEvaporatedCardsAction(amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShovelCoalPower(owner, amount);
    }
}
