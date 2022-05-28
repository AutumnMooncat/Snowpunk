package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModCardTempEverywhereAction;
import Snowpunk.actions.ModEngineStabilityPointAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class NorEaster extends AbstractEasyCard
{
    public final static String ID = makeID(NorEaster.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public NorEaster()
    {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        Wiz.atb(new ModCardTempEverywhereAction(-99));
        Wiz.atb(new ModEngineTempAction(-99));
        if (upgraded)
            Wiz.atb(new ModEngineStabilityPointAction(-99));
    }

    public void upp()
    {
        upgradeBaseCost(0);
        upgradedCost = true;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}