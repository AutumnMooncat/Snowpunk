package Snowpunk.cards.cardvars;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.SnowpunkMod.makeID;

public class Info extends DynamicVariable {

    @Override
    public String key() {
        return makeID("Info");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractEasyCard) card).isInfoModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractEasyCard) card).info;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractEasyCard) card).baseInfo;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractEasyCard) card).upgradedInfo;
    }

}