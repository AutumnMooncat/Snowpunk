package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Faster extends AbstractEasyCard {
    public final static String ID = makeID(Faster.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;

    public Faster() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        if (upgraded) {
            effect++;
        }

        if (effect > 0) {
            Wiz.atb(new DrawCardAction(effect));
            Wiz.atb(new GainEnergyAction(effect));
        }

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    public void upp() {
        uDesc();
    }
}