package Snowpunk.cards;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.LetItSnowPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class OLDLetItSnow extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(OLDLetItSnow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    boolean gainSnow = false;

    public OLDLetItSnow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        gainSnow = false;
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
//        int energy = EnergyPanel.getCurrentEnergy();// - SnowballPatches.Snowballs.getTrueAmount();
//        if (energy > 0)
//            Wiz.atb(new LoseEnergyAction(energy));
//        if (energy + magicNumber > 0)
//            Wiz.atb(new GainSnowballAction(energy + magicNumber));
        if (gainSnow)
            Wiz.atb(new GainSnowballAction(1));
        Wiz.applyToSelf(new LetItSnowPower(Wiz.adp(), 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            gainSnow = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}