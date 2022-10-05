package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cards.assemble.AdjectiveCore;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.cards.assemble.NameCore;
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

public class AssembleAction extends AbstractGameAction {

    public static final ArrayList<CoreCard> pickedCores = new ArrayList<>();
    boolean addToMaster;
    boolean random;
    int cores;
    int upgrades;
    int options;
    private Consumer<AssembledCard> onAssemble;

    public AssembleAction(int cores) {
        this(cores, 0, 3, true, false, c -> {
        });
    }

    public AssembleAction(int cores, int upgrades, int options) {
        this(cores, upgrades, options, true, false, c -> {
        });
    }

    public AssembleAction(int cores, int upgrades, int options, boolean addToMaster, boolean random, Consumer<AssembledCard> onAssemble) {
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
                refreshStats(ac);
                this.isDone = true;
            }
        });

        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                finalizeCard(ac);
                this.isDone = true;
            }
        });

        pickNamesForCard(ac, options);
        pickAdjectivesForCard(ac, options);
        for (int i = 0; i < cores; i++) {
            if (random)
                giveRandomCore(ac);
            else
                pickCoresForCard(ac, options);
        }
        this.isDone = true;
    }

    private void finalizeCard(AssembledCard card) {
        for (int i = 0; i < upgrades; i++) {
            card.upgrade();
        }
        AssembledCard copy = (AssembledCard) card.makeStatEquivalentCopy();
        copy.grabStats();
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

    private void refreshStats(AssembledCard card) {
        card.grabStats();
    }

    private static CardGroup getValidCores(AssembledCard card) {
        CardGroup validCores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        //int currentDoubledCost = card.doubledCost;
        for (CoreCard core : SnowpunkMod.cores) {
            if (core.canSpawn(card, AssembleAction.pickedCores)) {
                CoreCard copy = (CoreCard) core.makeCopy();
                validCores.addToTop(copy);
            }
        }
        return validCores;
    }

    private static void giveRandomCore(AssembledCard card) {
        CardGroup validCores = getValidCores(card);
        if (!validCores.isEmpty()) {
            AbstractCard core = validCores.getRandomCard(true);
            if (core instanceof CoreCard) {
                ((CoreCard) core).apply(card);
                validCores.removeCard(core);
                AssembleAction.pickedCores.add((CoreCard) core);
            }
        }
        card.grabStats();
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
                            if (c instanceof CoreCard) {
                                ((CoreCard) c).apply(card);
                                validCores.removeCard(c);
                                AssembleAction.pickedCores.add((CoreCard) c);
                            }
                        }
                    }));
                }
                card.grabStats();
                this.isDone = true;
            }
        });
    }

    private static void pickAdjectivesForCard(AssembledCard card, int options) {
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup validCores = getValidCores(card);
                if (!validCores.isEmpty()) {
                    ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
                    for (int i = 0; i < options; i++) {
                        AdjectiveCore newAdj = new AdjectiveCore();
                        int numChecked = 0;
                        while (cardsToPick.stream().anyMatch(c -> c.misc == newAdj.misc) && numChecked < AdjectiveCore.TEXT.length) {
                            newAdj.misc++;
                            if (newAdj.misc >= AdjectiveCore.TEXT.length)
                                newAdj.misc -= AdjectiveCore.TEXT.length;
                            numChecked++;
                        }
                        newAdj.initializeDescription();
                        cardsToPick.add(newAdj);
                    }
                    Wiz.att(new BetterSelectCardsCenteredAction(cardsToPick, 1, "", false, crd -> true, cards -> {
                        for (AbstractCard c : cards) {
                            if (c instanceof CoreCard) {
                                ((CoreCard) c).apply(card);
                                AssembleAction.pickedCores.add((CoreCard) c);
                            }
                        }
                    }));
                }
                card.grabStats();
                this.isDone = true;
            }
        });
    }

    private static void pickNamesForCard(AssembledCard card, int options) {
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup validCores = getValidCores(card);
                if (!validCores.isEmpty()) {
                    ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
                    for (int i = 0; i < options; i++) {
                        NameCore newName = new NameCore();
                        int numChecked = 0;
                        while (cardsToPick.stream().anyMatch(c -> c.misc == newName.misc) && numChecked < NameCore.TEXT.length) {
                            newName.misc++;
                            if (newName.misc >= NameCore.TEXT.length)
                                newName.misc -= NameCore.TEXT.length;
                            numChecked++;
                        }
                        newName.initializeDescription();
                        cardsToPick.add(newName);
                    }
                    Wiz.att(new BetterSelectCardsCenteredAction(cardsToPick, 1, "", false, crd -> true, cards -> {
                        for (AbstractCard c : cards) {
                            if (c instanceof CoreCard) {
                                ((CoreCard) c).apply(card);
                                AssembleAction.pickedCores.add((CoreCard) c);
                            }
                        }
                    }));
                }
                card.grabStats();
                this.isDone = true;
            }
        });
    }

}
