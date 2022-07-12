package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ColdWind extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ColdWind.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int CHILL = 4;
    private static final int UP_CHILL = 2;

    private boolean weak, aoe;

    public ColdWind() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHILL;
        info = baseInfo = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (aoe) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mo, new ChillPower(mo, magicNumber));
                    if (weak) {
                        Wiz.applyToEnemy(mo, new WeakPower(mo, magicNumber, false));
                    }
                }
            }
        } else {
            Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
            if (weak) {
                Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
            }
        }

    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_CHILL));
        addUpgradeData(this, () -> {
            weak = true;
            upgradeInfo(1);
        });
        addUpgradeData(this, () -> {
            aoe = true;
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
    }
}