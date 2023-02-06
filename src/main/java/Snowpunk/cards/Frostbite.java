package Snowpunk.cards;

import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Frostbite extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Frostbite.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 4, UP_COST = 3, MAGIC = 2, UP_MAGIC = 1;

    public Frostbite() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        info = baseInfo = 0;
        CardTemperatureFields.addInherentHeat(this, -2);
        CardModifierManager.addModifier(this, new ClockworkMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (info > 0) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mo, new FrostbitePower(mo, p, magicNumber));
                }
            }
        } else
            Wiz.applyToEnemy(m, new FrostbitePower(m, p, magicNumber));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> upgrade3());
    }

    private void upgrade3() {
        upgradeInfo(1);
        target = CardTarget.ALL_ENEMY;
        uDesc();
    }
}