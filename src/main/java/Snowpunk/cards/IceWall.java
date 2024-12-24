package Snowpunk.cards;

import Snowpunk.actions.DelayedMakeCopyAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class IceWall extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(IceWall.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;

    boolean upCost = false;

    public IceWall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = 8;
        magicNumber = baseMagicNumber = 3;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
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

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
//        int effect = this.energyOnUse;
//
//        if (player.hasRelic("Chemical X")) {
//            effect += ChemicalX.BOOST;
//            player.getRelic("Chemical X").flash();
//        }
//
//        int effect = getSnow();
//
//        for (int i = 0; i < effect; i++)
        applyPowers();
        blck();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        applyPowers();
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
        addUpgradeData(() -> {
            upgradeBlock(2);
            upgradeMagicNumber(1);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeMagicNumber(2));
        setDependencies(true, 2, 1, 0);
    }
}