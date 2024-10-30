package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.GearNextPower;
import Snowpunk.relics.Monocle;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Workshop extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Workshop.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, SPIRIT = 5, UP_SPIRIT = 2;

    public Workshop() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SPIRIT;
        secondMagic = baseSecondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BrassPower(p, magicNumber));
        Wiz.applyToSelf(new GearNextPower(p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SPIRIT));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        setDependencies(true, 2, 1);
    }
}