package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class FaLaLaLaLa extends AbstractEasyCard {
    public final static String ID = makeID(FaLaLaLaLa.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0, DMG = 7, BLOCK = 7;

    public FaLaLaLaLa() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
    }

    public FaLaLaLaLa(int holly) {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        damage = baseDamage = holly;
        block = baseBlock = holly;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void applyPowers() {
        getDesc();
        super.applyPowers();
    }

    @Override
    public void atTurnStart() {
        getDesc();
        super.atTurnStart();
    }

    private void getDesc() {
        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {

    }
}