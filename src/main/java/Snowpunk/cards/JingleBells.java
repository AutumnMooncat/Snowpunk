package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
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

    //private boolean noClank = false;

    public JingleBells() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ResetExhaustAction(this, false));
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        if (effect > 0)
            Wiz.applyToSelf(new JingleBellsPower(p, block, effect));

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
/*
        if (!noClank)
            Wiz.atb(new ClankAction(this));*/
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(3));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            //noClank = true;
            exhaust = false;
            uDesc();
        });
        setExclusions(1, 2);
    }
/*
    @Override
    public void onClank() {
        addToTop(new ResetExhaustAction(this, true));
    }*/
}