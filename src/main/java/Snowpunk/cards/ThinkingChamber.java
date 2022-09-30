package Snowpunk.cards;

import Snowpunk.actions.MultiUpgradeInHandAction;
import Snowpunk.actions.TinkerAction;
import Snowpunk.cardmods.FrostMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ProtectionPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ThinkingChamber extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ThinkingChamber.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int UPGRADE = 2;
    private static final int UP_TINKER = 99;
    private static final int PROTECT = 1;

    private boolean steam = false;

    public ThinkingChamber() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = UPGRADE;
        secondMagic = baseSecondMagic = PROTECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ProtectionPower(p, secondMagic));
        if (magicNumber < BaseMod.MAX_HAND_SIZE)
            Wiz.atb(new MultiUpgradeInHandAction(magicNumber, 1));
        else
            Wiz.atb(new ArmamentsAction(true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            upgradeMagicNumber(UP_TINKER);
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new FrostMod()));
    }
}