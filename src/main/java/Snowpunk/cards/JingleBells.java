package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.JingleBellsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class JingleBells extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(JingleBells.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1, BLOCK = 7;

    public JingleBells() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        Wiz.applyToSelf(new JingleBellsPower(p, block, effect));

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    /*
        @Override
        public void applyPowers() {
            super.applyPowers();
            int delta = damage - baseDamage;
            damage = baseDamage + delta * getX();
            if (damage < 0) {
                damage = 0;
            }
            isDamageModified = (damage != baseDamage);
        }

        @Override
        public void calculateCardDamage(AbstractMonster mo) {
            super.calculateCardDamage(mo);
            int delta = damage - baseDamage;
            damage = baseDamage + delta * getX();
            if (damage < 0) {
                damage = 0;
            }
            isDamageModified = (damage != baseDamage);
        }

        private int getX()
        {
            if(Wiz.adp() == null)
                return 0;

            int effect = getSnow() + EnergyPanel.getCurrentEnergy();

            if (Wiz.adp().hasRelic("Chemical X")) {
                effect += ChemicalX.BOOST;
                Wiz.adp().getRelic("Chemical X").flash();
            }
            return effect;
        }
    */
    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(3));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        setExclusions(1, 2);
    }
}