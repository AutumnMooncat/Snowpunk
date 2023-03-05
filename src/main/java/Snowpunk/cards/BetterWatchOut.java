package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class BetterWatchOut extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BetterWatchOut.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 10;
    private static final int UP_DMG = 3;

    public BetterWatchOut() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);

        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        for (int i = 0; i < effect; i++)
            addToBot(new MakeTempCardInDrawPileAction(makeStatEquivalentCopy(), 1, true, true));

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
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        setExclusions(1, 2);
    }
}