package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BoilerPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Boiler extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Boiler.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLOCK = 12, UP_BLOCK = 4;

    public Boiler() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        CardModifierManager.addModifier(this, new GearMod(3));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        int gears = getGears();
        if (gears > 0) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped() && monster.currentHealth > 0)
                    Wiz.atb(new ApplyPowerAction(monster, AbstractDungeon.player, new SingePower(monster, gears), gears));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
            upgradeBlock(2);
        });
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
            upgradeBlock(2);
        });
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
            upgradeBlock(2);
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}