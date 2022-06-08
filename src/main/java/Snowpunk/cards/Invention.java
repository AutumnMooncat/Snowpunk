package Snowpunk.cards;

import Snowpunk.actions.AssembleCardAction;
import Snowpunk.actions.TinkerAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Invention extends AbstractEasyCard {
    public final static String ID = makeID(Invention.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int CORES = 1;
    private static final int UP_CORES = 1;
    private static final int TINKER = 2;
    private static final int UP_TINKER = 1;

    public Invention() {
        super(ID, COST, TYPE, RARITY, TARGET);
        FleetingField.fleeting.set(this, true);
        baseMagicNumber = magicNumber = TINKER;
        baseSecondMagic = secondMagic = CORES;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AssembleCardAction(secondMagic, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_TINKER);
        upgradeSecondMagic(UP_CORES);
    }
}