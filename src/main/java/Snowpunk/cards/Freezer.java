package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FreezeNextCardPower;
import Snowpunk.powers.IcePower;
import Snowpunk.powers.LinkNextCardPower;
import Snowpunk.powers.RefrigeratePower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Freezer extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Freezer.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CARDS = 1, ICE = 1, UP_CARD = 1, UP_ICE = 1;

    public Freezer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = CARDS;
        secondMagic = baseSecondMagic = ICE;
        CardTemperatureFields.addInherentHeat(this, -2);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ModCardTempAction(magicNumber, -99, false));
        Wiz.applyToSelf(new IcePower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> upgradeMagicNumber(UP_CARD));
        addUpgradeData(() -> upgradeSecondMagic(UP_ICE));
    }
}