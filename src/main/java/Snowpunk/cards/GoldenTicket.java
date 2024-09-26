package Snowpunk.cards;

import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
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
        CardGroup deckWithoutTickets = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (!(card instanceof GoldenTicket))
                deckWithoutTickets.addToTop(card);
        }
        Wiz.atb(new BetterSelectCardsCenteredAction(deckWithoutTickets.group, magicNumber, "", false, card -> true, cards -> {
            for (AbstractCard c : cards) {
                Wiz.att(new VFXAction(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy())));
            }
        }));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}