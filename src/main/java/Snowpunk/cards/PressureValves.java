package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class PressureValves extends AbstractEasyCard {
    public final static String ID = makeID(PressureValves.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = -1;
    private static final int STACKS = 2;

    public PressureValves() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            int effect = this.energyOnUse;

            if (p.hasRelic("Chemical X")) {
                effect += ChemicalX.BOOST;
                p.getRelic("Chemical X").flash();
            }

            effect += magicNumber;

            if (effect > 0) {
                Wiz.applyToSelf(new PressureValvesPower(p, effect));
            }

            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        } else {
            Wiz.applyToSelf(new PressureValvesPower(p, magicNumber));
        }
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }
}