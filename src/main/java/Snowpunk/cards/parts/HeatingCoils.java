package Snowpunk.cards.parts;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatingCoils extends EasyModalChoiceCard
{
    public static final String ID = makeID(HeatingCoils.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public HeatingCoils(String name, String description, Runnable onUseOrChosen)
    {
        super(NAME, DESCRIPTION, () ->
        {
            //Add heat to card but idk how to get card to modify yet
        });
    }
}
