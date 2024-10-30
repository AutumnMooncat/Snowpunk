package Snowpunk.cards;

import Snowpunk.actions.ChangeCostAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Whistol extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Whistol.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0, DMG = 11, UP_DMG = 3;

    public Whistol() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        Wiz.atb(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> upgradeDamage(4));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank(AbstractMonster target) {
        addToTop(new ChangeCostAction(this, cost + 1));
    }

    @Override
    public void unClank(AbstractMonster target) {
        addToTop(new ChangeCostAction(this, Math.max(0, cost - 1)));
    }
}