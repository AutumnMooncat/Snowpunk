package Snowpunk.util;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GunManager
{

    public static final Logger logger = LogManager.getLogger(GunManager.class.getName());

    public static void RunGunUpdate(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null)
            return;
        logger.info("Gun check for " + card.name);
        if (player.masterDeck.contains(card))
            UpdateGunCardsMaster(card);
        else
            UpdateGunCardsCombat(card);
    }

    private static void UpdateGunCardsCombat(AbstractCard card)
    {
        logger.info("Found in Combat.");
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard c : player.exhaustPile.group)
        {
            if (c.tags.contains(CustomTags.GUN))
                UpdateCardToMatch(card, c);
        }
        for (AbstractCard c : player.hand.group)
        {
            if (c.tags.contains(CustomTags.GUN))
                UpdateCardToMatch(card, c);
        }
        for (AbstractCard c : player.discardPile.group)
        {
            if (c.tags.contains(CustomTags.GUN))
                UpdateCardToMatch(card, c);
        }
        for (AbstractCard c : player.drawPile.group)
        {
            if (c.tags.contains(CustomTags.GUN))
                UpdateCardToMatch(card, c);
        }
        for (AbstractCard c : player.limbo.group)
        {
            if (c.tags.contains(CustomTags.GUN))
                UpdateCardToMatch(card, c);
        }
    }

    private static void UpdateGunCardsMaster(AbstractCard card)
    {
        logger.info("Found in Deck.");
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard c : player.masterDeck.group)
        {
            if (c.tags.contains(CustomTags.GUN))
                UpdateCardToMatch(card, c);
        }
    }

    private static void UpdateCardToMatch(AbstractCard cardOrigin, AbstractCard cardToModify)
    {
        logger.info("Found Gun card " + cardToModify.name);
        int heatDiff = CardTemperatureFields.getCardHeat(cardOrigin) - CardTemperatureFields.getCardHeat(cardToModify);
        if (heatDiff != 0)
        {
            CardTemperatureFields.addHeat(cardToModify, heatDiff, false);

            logger.info("Attempted to modify temp for " + cardToModify.name);
        } else
            logger.info("Temp found to be the same for " + cardToModify.name);


        //TODO: ADD TINKER CHECK HERE
    }
}
