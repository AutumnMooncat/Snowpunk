package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.PressureValvesPower;
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

    private static final int COST = -1;
    private static final int NRG = 1;
    private static final int UP_NRG = 1;
    private static final int UP_DRAW = 1;

    public Faster() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = NRG;
        secondMagic = baseSecondMagic = 0;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        effect += secondMagic;

        if (effect > 0) {
            Wiz.atb(new DrawCardAction(effect));
        }
        Wiz.atb(new GainEnergyAction(magicNumber));

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeSecondMagic(UP_DRAW));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_NRG));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new VentMod()));
    }
}