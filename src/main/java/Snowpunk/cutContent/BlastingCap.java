package Snowpunk.cutContent;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BlastingCap extends AbstractEasyCard {
    public final static String ID = makeID(BlastingCap.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 3;
    private static final int UP_DMG = 1;

    public BlastingCap() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (pressure() > 0) {
            Wiz.atb(new DamageAction(m, new DamageInfo(p, damage*pressure(), damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}