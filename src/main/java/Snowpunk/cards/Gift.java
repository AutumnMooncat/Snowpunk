package Snowpunk.cards;

import Snowpunk.actions.GiftDiscoveryAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Gift extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Gift.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, MAGIC = 3, UP_MAGIC = 2;

    boolean free = false;

    public Gift() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GiftDiscoveryAction(magicNumber, free));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            free = true;
            if (exhaust) {
                uDesc();
            } else {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            }

        });
        addUpgradeData(() -> {
            exhaust = false;
            if (free) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            initializeDescription();
        });
        addUpgradeData(() -> upgradeMagicNumber(UP_MAGIC));
    }
}