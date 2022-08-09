package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.EmberForgePower;
import Snowpunk.powers.MadSciencePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class EmberForge extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(EmberForge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 2;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    private boolean overheat;

    public EmberForge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new EmberForgePower(p));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            this.isInnate = true;
            if (overheat) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            } else {
                uDesc();
            }

        });
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            this.overheat = true;
            if (isInnate) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            initializeDescription();
        });
    }
}