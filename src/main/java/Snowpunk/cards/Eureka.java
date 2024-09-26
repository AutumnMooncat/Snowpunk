package Snowpunk.cards;

import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.IncreaseModifiersAction;
import Snowpunk.actions.MoveCardToHandAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class Eureka extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Eureka.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Eureka() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup piles = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (!(card instanceof Eureka))
                piles.addToTop(card);
        }
        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (!(card instanceof Eureka))
                piles.addToTop(card);
        }
        for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
            if (!(card instanceof Eureka))
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