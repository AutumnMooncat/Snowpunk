package Snowpunk.relics.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface OnClankRelic {
    void onClank(AbstractCard card, AbstractCreature target);
}
