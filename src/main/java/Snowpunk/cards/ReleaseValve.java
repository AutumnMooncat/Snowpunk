package Snowpunk.cards;

import Snowpunk.actions.ExhumeEvaporatedCardAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class ReleaseValve extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ReleaseValve.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1, SINGE = 2, UP_SINGE = 2;
    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int numGears = getGears();
        if (numGears > 0)
            addToBot(new ExhumeEvaporatedCardAction(numGears, 1, false));
    }

    /*
        public void applyPowers() {
            int realBaseMagic = baseMagicNumber;
            baseMagicNumber += getGears() * EvaporatePanel.evaporatePile.size();
            super.applyPowers();
            magicNumber = baseMagicNumber;
            baseMagicNumber = realBaseMagic;
            isMagicNumberModified = magicNumber != baseMagicNumber;
        }
    */
    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}