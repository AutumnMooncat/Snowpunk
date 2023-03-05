package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class PressureValves extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(PressureValves.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;

    private static final int COST = 2;

    private boolean xUpgrade;

    public PressureValves() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PressureValvesPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> upgradeMagicNumber(1));
        setDependencies(true, 2, 0);
        setDependencies(true, 3, 1);
        setExclusions(2, 3);
    }
}