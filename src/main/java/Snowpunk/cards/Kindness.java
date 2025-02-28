package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Kindness extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Kindness.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    boolean AoE = false;

    public Kindness() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 8;
        selfRetain = true;
        AoE = false;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AoE) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped() && monster.currentHealth > 0) {
                    Wiz.atb(new HealAction(monster, p, 6));
                    Wiz.applyToEnemy(monster, new ChillPower(monster, magicNumber));
                }
            }
        } else {
            Wiz.atb(new HealAction(m, p, magicNumber));
            Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(3));
        addUpgradeData(() -> {
            AoE = true;
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}