package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class BlackIce extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BlackIce.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BONUS = 1;

    private boolean AOE = false;
    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    public BlackIce() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 0;
        info = baseInfo = 0;
        exhaust = true;
        //SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getSnow() + magicNumber > 0) {
            if (AOE) {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        Wiz.applyToEnemy(mo, new ChillPower(mo, getSnow() + magicNumber));
                        Wiz.applyToEnemy(mo, new WeakPower(mo, getSnow() + magicNumber, false));
                        Wiz.applyToEnemy(mo, new VulnerablePower(mo, getSnow() + magicNumber, false));
                    }
                }
            } else {
                Wiz.applyToEnemy(m, new ChillPower(m, getSnow() + magicNumber));
                Wiz.applyToEnemy(m, new WeakPower(m, getSnow() + magicNumber, false));
                Wiz.applyToEnemy(m, new VulnerablePower(m, getSnow() + magicNumber, false));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> upgrade2());
        addUpgradeData(() -> upgradeMagicNumber(BONUS));
    }

    private void upgrade2() {
        AOE = true;
        target = CardTarget.ALL_ENEMY;
        uDesc();
    }
}