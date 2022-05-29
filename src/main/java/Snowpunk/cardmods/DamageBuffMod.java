package Snowpunk.cardmods;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DamageBuffMod extends AbstractCardModifier {
    int amount;

    public DamageBuffMod(int amount) {
        this.amount = amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.baseDamage += amount;
        card.damage += amount;
        if (card instanceof AbstractEasyCard) {
            ((AbstractEasyCard) card).baseSecondDamage += amount;
            ((AbstractEasyCard) card).secondDamage += amount;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamageBuffMod(amount);
    }
}
