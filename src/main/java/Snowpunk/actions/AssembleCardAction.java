package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cardmods.MkMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class AssembleCardAction extends AbstractGameAction {
    public static final ArrayList<AbstractCoreCard> pickedCores = new ArrayList<>();
    int parts;
    int cores;
    boolean randomCores;
    boolean randomParts;

    public AssembleCardAction(int cores, int parts) {
        this(cores, parts, false, false);
    }

    public AssembleCardAction(int cores, int parts, boolean randomCores, boolean randomParts) {
        this.cores = cores;
        this.parts = parts;
        this.randomCores = randomCores;
        this.randomParts = randomParts;
    }

    @Override
    public void update() {
        pickedCores.clear();
        if (cores == 0) {
            return;
        }
        AssembledCard ac = new AssembledCard();
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                finalizeCard(ac);
                this.isDone = true;
            }
        });
        for (int i = 0 ; i < parts ; i++) {
            Wiz.att(new TinkerAction(ac, randomParts));
        }
        for (int i = 0 ; i < cores ; i++) {
            if (randomCores) {
                giveRandomCore(ac);
            } else {
                pickCoresForCard(ac);
            }
        }
        this.isDone = true;
    }

    private void finalizeCard(AssembledCard card) {
        CardModifierManager.removeModifiersById(card, MkMod.ID, true);
        AssembledCard copy = (AssembledCard) card.makeStatEquivalentCopy();
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(copy, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(copy, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(copy.makeSameInstanceOf(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    private static CardGroup getValidCores(AbstractCard card) {
        CardGroup validCores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int currentDoubledCost = card instanceof AssembledCard ? ((AssembledCard) card).doubledCost : 0;
        for (AbstractCoreCard core : SnowpunkMod.cores) {
            if (core.canSpawn(AssembleCardAction.pickedCores)) {
                AbstractCoreCard copy = (AbstractCoreCard) core.makeCopy();
                copy.prepRenderedCost(currentDoubledCost);
                copy.prepForSelection(AssembleCardAction.pickedCores);
                validCores.addToTop(copy);
            }
        }
        return validCores;
    }

    private static void giveRandomCore(AbstractCard card) {
        CardGroup validCores = getValidCores(card);
        if (!validCores.isEmpty()) {
            AbstractCard core = validCores.getRandomCard(true);
            if (core instanceof AbstractCoreCard) {
                ((AbstractCoreCard) core).apply(card);
                validCores.removeCard(core);
                AssembleCardAction.pickedCores.add((AbstractCoreCard) core);
            }
        }
    }

    private static void pickCoresForCard(AbstractCard card) {
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup validCores = getValidCores(card);
                if (!validCores.isEmpty()) {
                    ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
                    if (validCores.size() <= 3) {
                        cardsToPick.addAll(validCores.group);
                    } else {
                        for (int i = 0; i < 3; i++) {
                            AbstractCard c = validCores.getRandomCard(true);
                            validCores.removeCard(c);
                            cardsToPick.add(c);
                        }
                    }
                    Wiz.att(new BetterSelectCardsCenteredAction(cardsToPick, 1, "", false, crd -> true, cards -> {
                        for (AbstractCard c : cards) {
                            if (c instanceof AbstractCoreCard) {
                                ((AbstractCoreCard) c).apply(card);
                                validCores.removeCard(c);
                                AssembleCardAction.pickedCores.add((AbstractCoreCard) c);
                            }
                        }
                    }));
                }
                this.isDone = true;
            }
        });
    }

}
