package Snowpunk.cards;

import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.actions.MoveCardToHandAction;
import Snowpunk.actions.MoveCardToTopOfDrawPileAction;
import Snowpunk.cardmods.CargoMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class GoldenTicket extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(GoldenTicket.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public GoldenTicket() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        CardGroup deckWithoutTickets = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
//        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
//            if (!(card instanceof GoldenTicket))
//                deckWithoutTickets.addToTop(card);
//        }
//        Wiz.atb(new BetterSelectCardsCenteredAction(deckWithoutTickets.group, magicNumber, "", false, card -> true, cards -> {
//            for (AbstractCard c : cards) {
//                Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy())));
//            }
//        }));
//        Wiz.atb(new MakeCopyInHandAction(magicNumber));
//        if(!Wiz.adp().masterDeck.contains(cargo)){
//            Wiz.atb(new VFXAction(new ShowCardAndObtainEffect(cargo, Settings.WIDTH / 2f, Settings.HEIGHT / 2f)));
//        }
//        ArrayList<AbstractCard> cardsToChoose = new ArrayList<>();
//        for (AbstractCard c: Wiz.adp().hand.group) {
//            if(!(c instanceof GoldenTicket))
//                cardsToChoose.add(c);
//        }
//        Wiz.atb(new BetterSelectCardsCenteredAction(cardsToChoose, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0] + magicNumber + cardStrings.EXTENDED_DESCRIPTION[1], true, card -> true, cards -> {
//            for (AbstractCard c : cards) {
////                Wiz.adp().hand.removeCard(c);
//                CardModifierManager.addModifier(cargo, new CargoMod(c.makeStatEquivalentCopy()));
//            }
//        }));
//        Wiz.atb(new AbstractGameAction() {
//            @Override
//            public void update() {
//                if(CardModifierManager.hasModifier(cargo, CargoMod.ID)){
//                    CargoMod cargoMod = ((CargoMod)CardModifierManager.getModifiers(cargo, CargoMod.ID).get(0));
//                    if(cargoMod.cards.size() > 0)
//                    {
//                        Wiz.atb(new BetterSelectCardsCenteredAction(((CargoMod)CardModifierManager.getModifiers(cargo, CargoMod.ID).get(0)).cards, secondMagic, cardStrings.EXTENDED_DESCRIPTION[2] + secondMagic + cardStrings.EXTENDED_DESCRIPTION[3], true, card -> true, cards -> {
//                            for (AbstractCard c : cards) {
//                                cargoMod.cards.remove(c);
//                                cargoMod.updatePreviews(cargo);
//                                Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(c)));
//                            }
//                        }));
//                    }
//                }
//                isDone = true;
//            }
//        });

        CardGroup cardsToPick = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        cardsToPick.group.addAll(AbstractDungeon.player.drawPile.group);
        cardsToPick.group.addAll(AbstractDungeon.player.discardPile.group);
        cardsToPick.group.addAll(EvaporatePanel.evaporatePile.group);

        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c != this)
                cardsToPick.group.add(c.makeStatEquivalentCopy());
        }

        String text = magicNumber == 1 ? cardStrings.EXTENDED_DESCRIPTION[0] : cardStrings.EXTENDED_DESCRIPTION[1] + magicNumber + cardStrings.EXTENDED_DESCRIPTION[2];
        Wiz.atb(new BetterSelectCardsCenteredAction(cardsToPick.group, magicNumber, text, false, card -> true, cards -> {
            for (AbstractCard c : cards)
                Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy())));
        }));

    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}