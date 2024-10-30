package Snowpunk.cards;

import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.MoveCardToHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class StockingStuffer extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(StockingStuffer.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public StockingStuffer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup piles = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (!(card instanceof StockingStuffer))
                piles.addToTop(card);
        }
        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (!(card instanceof StockingStuffer))
                piles.addToTop(card);
        }
        for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
            if (!(card instanceof StockingStuffer))
                piles.addToTop(card);
        }
        Wiz.atb(new BetterSelectCardsCenteredAction(piles.group, magicNumber, "", false, card -> true, cards -> {
            for (AbstractCard c : cards) {
                Wiz.att(new MoveCardToHandAction(c));
            }
        }));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
    }
}