package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Frostbite extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Frostbite.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    public Frostbite() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addInherentHeat(this, -1);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getSnow() > 0) {
            if (target == CardTarget.ALL_ENEMY) {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (!mo.isDeadOrEscaped())
                        Wiz.applyToEnemy(mo, new StrengthPower(mo, -getSnow()));
                }
            } else
                Wiz.applyToEnemy(m, new StrengthPower(m, -getSnow()));
        }
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
    }

}