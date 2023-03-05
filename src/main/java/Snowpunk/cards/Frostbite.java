package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Frostbite extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Frostbite.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, MAGIC = 8, UP_MAGIC = 3;

    public Frostbite() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (target == CardTarget.ALL_ENEMY) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped())
                    Wiz.applyToEnemy(mo, new FrostbitePower(mo, p, magicNumber));
            }
        } else
            Wiz.applyToEnemy(m, new FrostbitePower(m, p, magicNumber));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
    }

}