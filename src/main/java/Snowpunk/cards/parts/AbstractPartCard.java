package Snowpunk.cards.parts;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Predicate;

public abstract class AbstractPartCard extends AbstractEasyCard {
    protected static final Predicate<AbstractCard> isPlayable = c -> c.cost != -2;
    protected static final Predicate<AbstractCard> hasBlockValue = c -> (c.baseBlock >= 0 && !(c instanceof RitualDagger));
    protected static final Predicate<AbstractCard> hasDamageValue = c -> c.baseDamage >= 0;
    protected static final Predicate<AbstractCard> hasMagicValue = c -> c.baseMagicNumber >= 0;
    protected static final Predicate<AbstractCard> isAttack = c -> c.type == CardType.ATTACK;
    protected static final Predicate<AbstractCard> isSkill = c -> c.type == CardType.SKILL;
    protected static final Predicate<AbstractCard> isPower = c -> c.type == CardType.POWER;
    protected static final Predicate<AbstractCard> nonZeroCost = c -> c.cost > 0 || c.cost == -1;
    protected static final Predicate<AbstractCard> greaterThanZeroCost = c -> c.cost > 0;

    public AbstractPartCard(String cardID, CardType type) {
        super(cardID, -2, type, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public abstract Predicate<AbstractCard> getFilter();

    public abstract void apply(AbstractCard card);

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
}
