package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cards.old_cores.AbstractCoreCard;
import Snowpunk.cards.old_cores.ARCHIVED_AssembledCard;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ARCHIVED_AssembleCardAction extends AbstractGameAction {

    public static final ArrayList<AbstractCoreCard> pickedCores = new ArrayList<>();
    boolean addToMaster;
    boolean random;
    int cores;
    int upgrades;
    int options;
    private Consumer<ARCHIVED_AssembledCard> onAssemble;

    public ARCHIVED_AssembleCardAction(int cores, int upgrades) {
        this(cores, upgrades, 3, true, false, c -> {
        });
    }

    public ARCHIVED_AssembleCardAction(int cores, int upgrades, int options) {
        this(cores, upgrades, options, true, false, c -> {
        });
    }

    public ARCHIVED_AssembleCardAction(int cores, int upgrades, int options, boolean addToMaster, boolean random, Consumer<ARCHIVED_AssembledCard> onAssemble) {
        this.cores = cores;
        this.upgrades = upgrades;
        this.options = options;
        this.addToMaster = addToMaster;
        this.random = random;
        this.onAssemble = onAssemble;
    }

    @Override
    public void update() {
        pickedCores.clear();
        ARCHIVED_AssembledCard ac = new ARCHIVED_AssembledCard();
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                finalizeCard(ac);
                this.isDone = true;
            }
        });
        for (int i = 0; i < cores; i++) {
            if (random) {
                giveRandomCore(ac);
            } else {
                pickCoresForCard(ac, options);
            }
        }
        this.isDone = true;
    }

    private void finalizeCard(ARCHIVED_AssembledCard card) {
        for (int i = 0; i < upgrades; i++) {
            card.upgrade();
        }
        ARCHIVED_AssembledCard copy = (ARCHIVED_AssembledCard) card.makeStatEquivalentCopy();
        onAssemble.accept(copy);
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(copy, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(copy, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
        }
        if (addToMaster) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(copy.makeSameInstanceOf(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
    }

    private static CardGroup getValidCores(ARCHIVED_AssembledCard card) {
        CardGroup validCores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        //int currentDoubledCost = card.doubledCost;
        /*for (AbstractCoreCard core : SnowpunkMod.cores) {
            if (core.canSpawn(card, ARCHIVED_AssembleCardAction.pickedCores)) {
                AbstractCoreCard copy = (AbstractCoreCard) core.makeCopy();
                //copy.prepRenderedCost(currentDoubledCost);
                copy.prepForSelection(card, ARCHIVED_AssembleCardAction.pickedCores);
                validCores.addToTop(copy);
            }
        }*/
        return validCores;
    }

    private static void giveRandomCore(ARCHIVED_AssembledCard card) {
        CardGroup validCores = getValidCores(card);
        if (!validCores.isEmpty()) {
            AbstractCard core = validCores.getRandomCard(true);
            if (core instanceof AbstractCoreCard) {
                ((AbstractCoreCard) core).apply(card);
                validCores.removeCard(core);
                ARCHIVED_AssembleCardAction.pickedCores.add((AbstractCoreCard) core);
            }
        }
    }

    private static void pickCoresForCard(ARCHIVED_AssembledCard card, int options) {
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup validCores = getValidCores(card);
                if (!validCores.isEmpty()) {
                    ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
                    if (validCores.size() <= options) {
                        cardsToPick.addAll(validCores.group);
                    } else {
                        for (int i = 0; i < options; i++) {
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
                                ARCHIVED_AssembleCardAction.pickedCores.add((AbstractCoreCard) c);
                            }
                        }
                    }));
                }
                this.isDone = true;
            }
        });
    }

}
