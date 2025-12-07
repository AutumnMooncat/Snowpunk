package Snowpunk.cards.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface InHandClankReaction {
    boolean willBlockClank(AbstractCard card);

    void postClank(AbstractCard card, boolean clanked);
}
