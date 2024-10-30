package Snowpunk.cards;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class LetItSnow extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(LetItSnow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public LetItSnow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int energy = EnergyPanel.getCurrentEnergy();// - SnowballPatches.Snowballs.getTrueAmount();
        if (energy > 0)
            Wiz.atb(new LoseEnergyAction(energy));
        if (energy + magicNumber > 0)
            Wiz.atb(new GainSnowballAction(energy + magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> {
            selfRetain = true;
            uDesc();
        });
    }
}