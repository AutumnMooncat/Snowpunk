package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.*;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Better extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Better.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;
    private static final int PARTS = 3;

    private boolean parts = false;

    public Better() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (parts) {
            Wiz.applyToSelf(new SparePartsPower(p, magicNumber));
        }
        Wiz.applyToSelf(new BetterPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            this.isInnate = true;
            if (parts) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            } else {
                uDesc();
            }
        });
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            this.parts = true;
            /*baseSecondMagic = secondMagic = 0;
            upgradeSecondMagic(PARTS);*/
            if (isInnate) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            initializeDescription();
        });
    }
}