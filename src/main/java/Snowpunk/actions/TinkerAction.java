package Snowpunk.actions;

import Snowpunk.patches.CustomTags;
import Snowpunk.powers.TouchOfBrassPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static Snowpunk.SnowpunkMod.makeID;

public class TinkerAction extends AbstractGameAction {
    public static final String ID = makeID("Tinker");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private AbstractCard card;
    private boolean random;

    public TinkerAction(AbstractCard card) {
        this.card = card;
    }

    public TinkerAction(int amount, boolean random) {
        this.amount = amount;
        this.random = random;
    }

    @Override
    public void update() {
        if (card != null) {
            tinkerCard(card);
        } else {
            if (amount >= Wiz.adp().hand.size()) {
                Iterator<AbstractCard> it = Wiz.adp().hand.group.iterator();
                while (it.hasNext()) {
                    AbstractCard c = it.next();
                    it.remove();
                    tinkerCard(c);
                }
            } else if (random) {
                for (int i = 0 ; i < amount ; i++) {
                    tinkerCard(Wiz.adp().hand.getRandomCard(true));
                }
            } else {
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                for (AbstractCard c : Wiz.adp().hand.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
                }

                Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, amount, amount == 1 ? TEXT[0] : TEXT[1] + amount + TEXT[2], false, c -> true, cards -> {
                    for (AbstractCard c : cards) {
                        tinkerCard(cardMap.get(c));
                    }
                }));
            }
        }
        this.isDone = true;
    }

    public static void tinkerCard(AbstractCard c) {
        if (AbstractDungeon.player.hoveredCard == c) {
            AbstractDungeon.player.releaseCard();
        }
        if (!c.hasTag(CustomTags.MENDING)) {
            c.unhover();
            c.untip();
            c.stopGlowing();
            Wiz.adp().hand.group.remove(c);
            AbstractDungeon.player.onCardDrawOrDiscard();
            if (Wiz.adp().cardInUse == c) {
                c.purgeOnUse = true;
            } else {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
            }
        }
        int parts = 1;
        if (c instanceof BranchingUpgradesCard) {
            parts += Math.abs(c.timesUpgraded);
        } else if (c instanceof MultiUpgradeCard) {
            parts += ((MultiUpgradeCard) c).upgradesPerformed();
        } else {
            parts += c.timesUpgraded;
        }
        Wiz.applyToSelf(new TouchOfBrassPower(Wiz.adp(), parts));
    }
}
