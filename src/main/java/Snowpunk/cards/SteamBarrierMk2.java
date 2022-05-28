package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamBarrierMk2 extends AbstractEasyCard
{
    public final static String ID = makeID(SteamBarrierMk2.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 8, UPG_BLOCK = 3;

    public SteamBarrierMk2()
    {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        blck();
    }

    public void upp()
    {
        upgradeBlock(UPG_BLOCK);
        upgradedBlock = true;
        initializeDescription();
    }
}