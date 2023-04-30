package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.AshesPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Wildfire extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Wildfire.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int EFFECT = 1;

    public Wildfire() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AshesPower(p, magicNumber));
        if (secondMagic > 0)
            Wiz.applyToSelf(new FireballPower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> {
            secondMagic = baseSecondMagic = 0;
            upgradeSecondMagic(1);
            uDesc();
        });
    }
}