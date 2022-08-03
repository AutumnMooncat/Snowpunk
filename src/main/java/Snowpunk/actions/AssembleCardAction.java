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
    public enum AssembleType {
        INVENTION(1, 1),
        CREATION(2, 1),
        MACHINATION(2, 2);

        AssembleType(int cores, int parts) {
            this.cores = cores;
            this.parts = parts;
        }

        private final int cores;
        private final int parts;

        public int getCores() {
            return cores;
        }

        public int getParts() {
            return parts;
        }
    }

    public static final ArrayList<AbstractCoreCard> pickedCores = new ArrayList<>();
    AssembleType type;
    boolean addToMaster;
    boolean random;
    private Consumer<AssembledCard> onAssemble;

    public AssembleCardAction(AssembleType type) {
        this(type, true, false, c -> {});
    }

    public AssembleCardAction(AssembleType type, boolean addToMaster, boolean random) {
        this(type, addToMaster, random, c -> {});
    }

    public AssembleCardAction(AssembleType type, boolean addToMaster, boolean random, Consumer<AssembledCard> onAssemble) {
        this.type = type;
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
        for (int i = 0 ; i < type.getParts() ; i++) {
            Wiz.att(new TinkerActionOLD(ac, random));
        }
        for (int i = 0 ; i < type.getCores() ; i++) {
            if (random) {
                giveRandomCore(ac);
            } else {
                pickCoresForCard(ac, type);
            }
        }
        this.isDone = true;
    }

    private void finalizeCard(AssembledCard card) {
        CardModifierManager.removeModifiersById(card, MkMod.ID, true);
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

    /*private static CardGroup getWeightedCores(AssembledCard card, AssembleType type) {
        CardGroup cores = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int currentDoubledCost = card.doubledCost;
        int toSelect = 3;
        ArrayList<AbstractCoreCard> commons = new ArrayList<>();
        ArrayList<AbstractCoreCard> uncommons = new ArrayList<>();
        ArrayList<AbstractCoreCard> rares = new ArrayList<>();
        for (AbstractCoreCard core : SnowpunkMod.cores) {
            if (core.canSpawn(card, AssembleCardAction.pickedCores)) {
                AbstractCoreCard copy = (AbstractCoreCard) core.makeCopy();
                //copy.prepRenderedCost(currentDoubledCost);
                copy.prepForSelection(card, AssembleCardAction.pickedCores);
                switch (copy.dropRarity) {
                    case COMMON:
                        commons.add(copy);
                        break;
                    case UNCOMMON:
                        uncommons.add(copy);
                        break;
                    case RARE:
                        rares.add(copy);
                        break;
                }
            }
        }
        if (type == AssembleType.MACHINATION && AssembleCardAction.pickedCores.isEmpty() && !rares.isEmpty()) {
            for (int i = 0 ; i < toSelect ; i++) {
                if (!rares.isEmpty()) {
                    cores.addToTop(rares.remove(AbstractDungeon.cardRandomRng.random(rares.size()-1)));
                }
            }
            return cores;
        }
        ArrayList<AbstractCard.CardRarity> options = new ArrayList<>();
        for (int i = 0 ; i < toSelect ; i ++) {
            if (!commons.isEmpty()) {
                options.add(AbstractCard.CardRarity.COMMON);
                options.add(AbstractCard.CardRarity.COMMON);
                options.add(AbstractCard.CardRarity.COMMON);
                options.add(AbstractCard.CardRarity.COMMON);
            }
            if (!uncommons.isEmpty()) {
                options.add(AbstractCard.CardRarity.UNCOMMON);
                options.add(AbstractCard.CardRarity.UNCOMMON);
            }
            if (!rares.isEmpty()) {
                options.add(AbstractCard.CardRarity.RARE);
            }
            if (options.isEmpty()) {
                return cores;
            }
            AbstractCard.CardRarity selection = options.get(AbstractDungeon.cardRandomRng.random(options.size()-1));
            switch (selection) {
                case COMMON:
                    cores.addToTop(commons.remove(AbstractDungeon.cardRandomRng.random(commons.size()-1)));
                    break;
                case UNCOMMON:
                    cores.addToTop(uncommons.remove(AbstractDungeon.cardRandomRng.random(uncommons.size()-1)));
                    break;
                case RARE:
                    cores.addToTop(rares.remove(AbstractDungeon.cardRandomRng.random(rares.size()-1)));
                    break;
            }
            options.clear();
        }
        return cores;
    }*/

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

    private static void pickCoresForCard(AssembledCard card, AssembleType type) {
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
