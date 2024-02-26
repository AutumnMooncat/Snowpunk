package Snowpunk.cards;

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
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1, SINGE = 4, UP_SINGE = 2, PLUS_SINGE = 2;

    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SINGE;
        secondMagic = baseSecondMagic = PLUS_SINGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        Wiz.applyToEnemy(m, new SingePower(m, p, magicNumber));
    }

    public void applyPowers() {
        int realBaseMagic = baseMagicNumber;
        baseMagicNumber += secondMagic * EvaporatePanel.evaporatePile.size();
        super.applyPowers();
        magicNumber = baseMagicNumber;
        baseMagicNumber = realBaseMagic;
        isMagicNumberModified = magicNumber != baseMagicNumber;
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}