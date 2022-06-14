package Snowpunk.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhumeMostRecentAction extends AbstractGameAction
{

    private AbstractCard card;

    public ExhumeMostRecentAction(AbstractCard card, int amount)
    {
        this.amount = amount;
        this.card = card;
    }

    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        for (int i = 0; i < amount; i++)
        {
            CardGroup searchGroup = player.exhaustPile;
            if (searchGroup.contains(card))
                searchGroup.removeCard(card);
            if (searchGroup.size() > 0)
            {
                AbstractCard card = player.exhaustPile.getSpecificCard(searchGroup.getTopCard());
                if (card != null)
                {
                    card.unhover();
                    card.unfadeOut();
                    card.lighten(true);
                    card.fadingOut = false;
                    if (player.hand.size() < BaseMod.MAX_HAND_SIZE)
                        player.exhaustPile.moveToHand(card, player.exhaustPile);
                    else
                        player.exhaustPile.moveToDiscardPile(card);
                }
            }
        }
        isDone = true;
    }
}
