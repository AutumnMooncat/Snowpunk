package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.InHandClankReaction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.GeneratorPower;
import Snowpunk.powers.TheSnowmanPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Generator extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Generator.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;

    boolean secondEnergy = false;

    public Generator() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        secondEnergy = false;
    }

//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
//        return false;// 39
//    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            p.getRelic("Chemical X").flash();
        }

        if (magicNumber > 0)
            effect += magicNumber;

        if (effect > 0) {
//            Wiz.applyToSelf(new DrawCardNextTurnPower(Wiz.adp(), effect));
//            Wiz.applyToSelf(new SnowNextTurnPower(Wiz.adp(), effect));

//            Wiz.atb(new ApplyCardModifierAction(p.hand, secondMagic, new HatMod(effect)));
            Wiz.applyToSelf(new GeneratorPower(Wiz.adp(), effect));
        }

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }

        Wiz.atb(new GainEnergyAction(secondEnergy ? 2 : 1));
    }
//
//    @Override
//    public boolean willBlockClank(AbstractCard card) {
//        return false;
//    }
//
//    @Override
//    public void postClank(AbstractCard card, boolean clanked) {
//        if(clanked || info > 0)
//        {
//            Wiz.atb(new DiscardSpecificCardAction(this));
//            Wiz.atb(new DrawCardAction(magicNumber));
//            Wiz.atb(new GainEnergyAction(secondMagic));
//        }
//    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            secondEnergy = true;
            uDesc();
        });
    }
}