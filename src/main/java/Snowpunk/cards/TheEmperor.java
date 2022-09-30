package Snowpunk.cards;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.atb;

public class TheEmperor extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TheEmperor.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;
    private static final int UP_COST = 2;
    private static final int DMG = 5;
    private static final int UP_DMG = 1;
    private static final int HITS = 5;
    private static final int UP_HITS = 1;

    public TheEmperor() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = HITS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i ++) {
            if (isMultiDamage) {
                atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
            } else {
                atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            }
        }
    }

    //TODO non strict upgrades don't render well
    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            upgradeDamage(UP_DMG);
            upgradeMagicNumber(UP_HITS);
        }, true, new int[]{}, new int[]{1});
        addUpgradeData(this, () -> {
            this.isMultiDamage = true;
            this.target = CardTarget.ALL_ENEMY;
            uDesc();
        }, true, new int[]{}, new int[]{0});
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST), false, 0, 1);
    }
}