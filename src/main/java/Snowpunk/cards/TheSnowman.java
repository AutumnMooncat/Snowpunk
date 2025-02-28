package Snowpunk.cards;

import Snowpunk.actions.AddHatsToRandomCardsAction;
import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SnowNextTurnPower;
import Snowpunk.powers.TheSnowmanPower;
import Snowpunk.util.Wiz;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class TheSnowman extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TheSnowman.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;

    public boolean freeUpgrade = false;

    public TheSnowman() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        secondMagic = baseSecondMagic = 1;
        exhaust = true;
    }

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

            Wiz.atb(new ApplyCardModifierAction(p.hand, secondMagic, new HatMod(effect)));
        }
//            Wiz.applyToSelf(new TheSnowmanPower(Wiz.adp(), effect));

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> upgradeSecondMagic(1));
        addUpgradeData(() ->
        {
            exhaust = false;
            uDesc();
        });
    }
}