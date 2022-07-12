package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.BurnPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Scald extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Scald.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 6;
    private static final int UP_DMG = 2;
    private static final int BURN = 3;
    private static final int UP_BURN = 1;

    private boolean doubleHit = false;

    public Scald() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = BURN;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (doubleHit) {
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
        }
        if (info == 1) {
            for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                if (!mon.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mon, new BurnPower(mon, p, magicNumber));
                }
            }
        } else {
            Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            upgradeDamage(UP_DMG);
            upgradeMagicNumber(UP_BURN);
        });
        addUpgradeData(this, () -> upgradeInfo(1));
        addUpgradeData(this, () -> {
            upgradeDamage(-2);
            doubleHit = true;
            uDesc();
        });
    }
}