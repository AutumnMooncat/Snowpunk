package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.BurnPower;
import Snowpunk.powers.SootPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static Snowpunk.SnowpunkMod.makeID;

public class Ignite extends AbstractEasyCard {
    public final static String ID = makeID(Ignite.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BURN = 4;
    private static final int UP_BURN = 3;

    public Ignite() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BURN;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                int amount = magicNumber;
                if (mo.hasPower(SootPower.POWER_ID)) {
                    amount += mo.getPower(SootPower.POWER_ID).amount;
                }
                Wiz.applyToEnemy(mo, new BurnPower(mo, p, amount));
            }
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.hasPower(SootPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    public void upp() {
        upgradeMagicNumber(UP_BURN);
    }
}