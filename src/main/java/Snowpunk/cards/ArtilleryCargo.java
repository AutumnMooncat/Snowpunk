package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ArtilleryCargo extends AbstractEasyCard {
    public final static String ID = makeID(ArtilleryCargo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 5;
    private static final int DMG = 42;
    private static final int UP_DMG = 16;

    public ArtilleryCargo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}