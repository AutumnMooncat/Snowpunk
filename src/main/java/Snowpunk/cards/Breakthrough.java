package Snowpunk.cards;

import Snowpunk.actions.AssembleCardAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.BetterCreationPower;
import Snowpunk.powers.BetterInventionPower;
import Snowpunk.powers.BetterMachinationPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Breakthrough extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Breakthrough.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private boolean creation = false;
    private boolean machination = false;

    public Breakthrough() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (machination) {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.MACHINATION, false, true, c -> c.setCostForTurn(0)));
        } else if (creation) {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.CREATION, false, true, c -> c.setCostForTurn(0)));
        } else {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.INVENTION, false, true, c -> c.setCostForTurn(0)));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            creation = true;
            uDesc();
        });
        addUpgradeData(this, () -> {
            machination = true;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        },1);
    }
}