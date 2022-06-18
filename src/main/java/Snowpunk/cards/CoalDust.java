package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SootPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class CoalDust extends AbstractEasyCard {
    public final static String ID = makeID(CoalDust.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int SOOT = 2;

    public CoalDust() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = SOOT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mo, new SootPower(mo, magicNumber));
                }
            }
        } else {
            Wiz.applyToEnemy(m, new SootPower(m, magicNumber));
        }

    }

    public void upp() {
        target = CardTarget.ALL_ENEMY;
        uDesc();
    }
}