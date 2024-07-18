package Snowpunk.actions;

import Snowpunk.cardmods.HatMod;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static Snowpunk.SnowpunkMod.makeID;

public class ApplyCardModifierAction extends AbstractGameAction {
    public static final String ID = makeID("Enhance");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private AbstractCardModifier mod;
    private AbstractCard card = null;
    private boolean hand = false;
    private CardGroup group = null;
    private int num = 1;
    private AbstractCard.CardType typeFilter = null;
    private final Set<AbstractCard> cardsApplied = new HashSet<>();

    public ApplyCardModifierAction(AbstractCard card, AbstractCardModifier mod) {
        this.mod = mod;
        this.card = card;
    }

    public ApplyCardModifierAction(boolean hand, AbstractCardModifier mod) {
        this.mod = mod;
        this.hand = hand;
    }

    public ApplyCardModifierAction(CardGroup group, AbstractCardModifier mod) {
        this(group, 1, mod);
    }

    public ApplyCardModifierAction(CardGroup group, int num, AbstractCardModifier mod) {
        this.mod = mod;
        this.group = group;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        this.num = num;
    }

    public ApplyCardModifierAction(AbstractCard.CardType type, AbstractCardModifier mod) {
        this.mod = mod;
        hand = true;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        typeFilter = type;
    }

    @Override
    public void update() {
        if (hand) {
            for (AbstractCard c : Wiz.adp().hand.group) {
                if (typeFilter == null || c.type == typeFilter) {
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }
            isDone = true;
        } else if (group != null) {
            pickFromGroup(group);
        } else {
            if (card == null)
                card = Wiz.adp().hand.getRandomCard(true);
            if (card == null) {
                isDone = true;
                return;
            }
            UUID uuid = card.uuid;
            for (AbstractCard c : GetAllInBattleInstances.get(uuid)) {
                CardModifierManager.addModifier(c, mod.makeCopy());
                c.superFlash();
            }
            /*if(Wiz.adp().cardInUse.uuid == uuid && cardsApplied.add(Wiz.adp().cardInUse))
            {
                CardModifierManager.addModifier(Wiz.adp().cardInUse, mod.makeCopy());
                Wiz.adp().cardInUse.superFlash();
            }
            for (AbstractCard c : Wiz.adp().hand.group) {
                if(c.uuid == uuid && cardsApplied.add(c)){
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }
            for (AbstractCard c : Wiz.adp().limbo.group) {
                if(c.uuid == uuid && cardsApplied.add(c)){
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }
            for (AbstractCard c : Wiz.adp().discardPile.group) {
                if(c.uuid == uuid && cardsApplied.add(c)){
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }
            for (AbstractCard c : Wiz.adp().drawPile.group) {
                if(c.uuid == uuid && cardsApplied.add(c)){
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }
            for (AbstractCard c : Wiz.adp().exhaustPile.group) {
                if(c.uuid == uuid && cardsApplied.add(c)){
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }
            for (AbstractCard c : EvaporatePanel.evaporatePile.group) {
                if(c.uuid == uuid && cardsApplied.add(c)){
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    c.superFlash();
                }
            }*/
            isDone = true;
        }
    }

    private void pickFromGroup(CardGroup group) {
        if (duration == startDuration) {
            if (group.size() == 0) {
                isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], num, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                CardModifierManager.addModifier(c, mod);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
