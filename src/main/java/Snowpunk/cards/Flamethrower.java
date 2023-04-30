package Snowpunk.cards;

import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Flamethrower extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Flamethrower.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private boolean aoe = false;
    private static final int COST = 2, SINGE = 6, UP_SINGE = 3;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
        }
        return Tooltip;
    }

    public Flamethrower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = SINGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (aoe) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped())
                    addToBot(new ApplyPowerAction(mo, p, new SingePower(mo, p, magicNumber), magicNumber));
            }
        } else
            addToBot(new ApplyPowerAction(m, p, new SingePower(m, p, magicNumber), magicNumber));
        Wiz.applyToSelf(new FireballPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SINGE));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> {
            aoe = true;
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
    }
}