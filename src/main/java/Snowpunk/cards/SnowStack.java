package Snowpunk.cards;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowStack extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SnowStack.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BLOCK = 8;
    private static final int UP_BLOCK = 4;

    public SnowStack() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLOCK;
        magicNumber = baseMagicNumber = 2;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        blck();
    }

    public void applyPowers() {
        int realBaseBlock = baseBlock;
        baseBlock += magicNumber * getSnow();
        super.applyPowers();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UP_BLOCK));
        addUpgradeData(() -> upgradeMagicNumber(2));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
        setDependencies(true, 2, 0, 1);
    }
}