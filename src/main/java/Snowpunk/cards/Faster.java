package Snowpunk.cards;

import Snowpunk.cardmods.DupeMod;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.OverheatNextCardPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Faster extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Faster.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, OVERHEATED_CARDS = 1, UP_CARDS = 1;

    public Faster() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = OVERHEATED_CARDS;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new OverheatNextCardPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> upgradeMagicNumber(UP_CARDS));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new DupeMod()));
    }
}