package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.ProtectionPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ThinkingChamber extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ThinkingChamber.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int TINKER = 2;
    private static final int UP_TINKER = 1;
    private static final int PROTECT = 1;
    private static final int UP_PROTECT = 1;

    private boolean steam = false;

    public ThinkingChamber() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = TINKER;
        secondMagic = baseSecondMagic = PROTECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ProtectionPower(p, secondMagic));
        if (steam) {
            Wiz.applyToSelf(new SteamPower(p, secondMagic));
        }
        Wiz.atb(new TinkerAction(magicNumber, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_TINKER));
        addUpgradeData(this, () -> upgradeSecondMagic(UP_PROTECT), new int[]{0}, new int[]{2});
        addUpgradeData(this, () -> {
            steam = true;
            uDesc();
        }, new int[]{0}, new int[]{1});
    }
}