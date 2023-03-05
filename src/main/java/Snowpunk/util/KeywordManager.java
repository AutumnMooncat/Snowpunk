package Snowpunk.util;

import Snowpunk.SnowpunkMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public class KeywordManager {

    public static final String ID = SnowpunkMod.makeID(KeywordManager.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String EVAPORATE = cardStrings.EXTENDED_DESCRIPTION[0];
    public static String CONDENSE = cardStrings.EXTENDED_DESCRIPTION[1];
    public static String CHRISTMAS_SPIRIT = cardStrings.EXTENDED_DESCRIPTION[2];
    public static String WREATH = cardStrings.EXTENDED_DESCRIPTION[3];
    public static String TEMP = cardStrings.EXTENDED_DESCRIPTION[4];
    public static String GEAR = cardStrings.EXTENDED_DESCRIPTION[5];
    public static String SNOW = cardStrings.EXTENDED_DESCRIPTION[6];
    public static String HAT = cardStrings.EXTENDED_DESCRIPTION[7];
}
