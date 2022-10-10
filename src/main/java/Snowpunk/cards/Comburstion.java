package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Comburstion extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Comburstion.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 10;
    private static final int UP_DMG = 4;
    private static final int BURN = 5;
    private static final int UP_BURN = 2;

    public Comburstion() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = BURN;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                Wiz.applyToEnemy(mo, new SingePower(mo, p, magicNumber));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_BURN));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1));
    }
}