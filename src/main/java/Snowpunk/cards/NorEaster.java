package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModCardTempEverywhereAction;
import Snowpunk.actions.ModEngineStabilityPointAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class NorEaster extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(NorEaster.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;
    private static final int UP_COST = 0;

    private boolean allCards = false;

    public NorEaster() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        exhaust = true;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        effect += magicNumber;

        if (effect > 0) {
            Wiz.applyToSelf(new SnowballPower(p, effect));
        }

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}