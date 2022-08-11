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
import java.util.function.Consumer;

public class AssembleCardAction extends AbstractGameAction {

    public static final ArrayList<AbstractCoreCard> pickedCores = new ArrayList<>();
    boolean addToMaster;
    boolean random;
    int cores;
    int upgrades;
    int options;
    private Consumer<AssembledCard> onAssemble;

    public AssembleCardAction(int cores, int upgrades) {
        this(cores, upgrades, 3, true, false, c -> {
        });
    }

    public AssembleCardAction(int cores, int upgrades, int options) {
        this(cores, upgrades, options, true, false, c -> {
        });
    }

    public AssembleCardAction(int cores, int upgrades, int options, boolean addToMaster, boolean random, Consumer<AssembledCard> onAssemble) {
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
        AssembledCard ac = new AssembledCard();
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

    private void finalizeCard(AssembledCard card) {
        for (int i = 0 ; i < upgrades ; i++) {
            //card.upgrade();
        }
        AssembledCard copy = (AssembledCard) card.makeStatEquivalentCopy();
        onAssemble.accept(copy);
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(copy, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(copy, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
        if (addToMaster) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(copy.makeSameInstanceOf(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
    }

    private static CardGroup getValidCores(AssembledCard card) {
        CardGroup validCores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        //int currentDoubledCost = card.doubledCost;
        for (AbstractCoreCard core : SnowpunkMod.cores) {
            if (core.canSpawn(card, AssembleCardAction.pickedCores)) {
                AbstractCoreCard copy = (AbstractCoreCard) core.makeCopy();
                //copy.prepRenderedCost(currentDoubledCost);
                copy.prepForSelection(card, AssembleCardAction.pickedCores);
                validCores.addToTop(copy);
            }
        }
        return validCores;
    }

    private static void giveRandomCore(AssembledCard card) {
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

    private static void pickCoresForCard(AssembledCard card, int options) {
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
