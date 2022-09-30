package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FreezeNextCardPower;
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

    private static final int COST = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Freezer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
        CardTemperatureFields.addInherentHeat(this, -2);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ModCardTempAction(magicNumber, -99, false));
        Wiz.atb(new ModEngineTempAction(-1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(0));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_EFFECT));
        addUpgradeData(this, () ->
        {
            exhaust = false;
            uDesc();
        });
    }
}