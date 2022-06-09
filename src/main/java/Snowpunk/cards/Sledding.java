package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Sledding extends AbstractEasyCard {
    public final static String ID = makeID(Sledding.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int UP_COST = 0;
    private static final int DMG = 2;
    private static final int UP_DMG = 4;

    public Sledding() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getSnow() > 0) {
            for (int i = 0 ; i < multiDamage.length ; i++) {
                multiDamage[i] *= getSnow();
            }
            Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        CardTemperatureFields.addInherentHeat(this, -1);
        //upgradeDamage(UP_DMG);
    }
}