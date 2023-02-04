package Snowpunk.cards;

import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.HollyPower;
import Snowpunk.powers.SnowballPower;
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

public class DeckTheHalls extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(DeckTheHalls.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, SPIRIT = 8, UP_SPIRIT = 4;


    public DeckTheHalls() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SPIRIT;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new HollyPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SPIRIT));
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}