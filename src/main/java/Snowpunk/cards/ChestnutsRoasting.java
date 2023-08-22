package Snowpunk.cards;

import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
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

public class ChestnutsRoasting extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChestnutsRoasting.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int CARDS = 2;
    private static final int UP_CARDS = 1;
    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
        }
        return Tooltip;
    }

    public ChestnutsRoasting() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = CARDS;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Wiz.atb(new ModCardTempAction(magicNumber, 1, false));
        Wiz.applyToSelf(new FireballPower(p, magicNumber));
    }

/*
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(Tooltip == null)
        {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.TEMP), BaseMod.getKeywordDescription(KeywordManager.TEMP)));
        }
        return Tooltip;
    }*/

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(UP_CARDS));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new EverburnMod()));
    }
}