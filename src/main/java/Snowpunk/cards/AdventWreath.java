package Snowpunk.cards;

import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class AdventWreath extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(AdventWreath.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, SPIRIT = 4, UP_SPIRIT = 2, FIRE = 1, UP_FIRE = 1;

    public AdventWreath() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SPIRIT;
        secondMagic = baseSecondMagic = FIRE;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FireballPower(p, secondMagic));
        Wiz.atb(new ChangeChristmasSpiritAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> upgradeSecondMagic(UP_FIRE));
        addUpgradeData(() -> upgradeMagicNumber(UP_SPIRIT));
        addUpgradeData(() -> upgradeSecondMagic(UP_FIRE));
        setDependencies(true, 3, 1);
        addUpgradeData(() -> upgradeSecondMagic(UP_FIRE));
        setDependencies(true, 4, 3);
    }
}