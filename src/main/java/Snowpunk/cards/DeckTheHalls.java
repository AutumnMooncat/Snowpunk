package Snowpunk.cards;

import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.HollyPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class DeckTheHalls extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(DeckTheHalls.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1, SPIRIT = 5, UP_SPIRIT = 2;


    public DeckTheHalls() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SPIRIT;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int spirit = getSnow() * magicNumber;
        if (spirit > 0)
            Wiz.atb(new ChangeChristmasSpiritAction(spirit));

        if (secondMagic > 0)
            Wiz.applyToSelf(new SnowballPower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_SPIRIT));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(this, () -> {
            secondMagic = baseSecondMagic = 0;
            upgradeSecondMagic(1);
            uDesc();
        });
    }
}