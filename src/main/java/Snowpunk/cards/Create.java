package Snowpunk.cards;

import Snowpunk.actions.AssembleCardAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Create extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Create.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private boolean machination = false;
    private boolean random = false;

    public Create() {
        super(ID, COST, TYPE, RARITY, TARGET);
        FleetingField.fleeting.set(this, true);
        tags.add(CardTags.HEALING); // We don't want this generated in combat
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (machination) {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.MACHINATION, !random, random));
        } else {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.CREATION, !random, random));
        }
    }

    public void upp() {
        uDesc();
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            machination = true;
            if (random) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            } else {
                uDesc();
            }
        });
        addUpgradeData(this, () -> {
            random = true;
            FleetingField.fleeting.set(this, false);
            if (machination) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            } else {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                initializeDescription();
            }
        });
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST), 1);
    }
}