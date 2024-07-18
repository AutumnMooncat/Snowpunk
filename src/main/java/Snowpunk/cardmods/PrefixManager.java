package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.KeywordManager;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class PrefixManager extends AbstractCardModifier {
    public static final String ID = makeID(PrefixManager.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public PrefixManager() {
        this.priority = -1;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        StringBuilder sb = new StringBuilder();
        int temp = CardTemperatureFields.getCardHeat(card);
        if (temp != 0) {
            switch (temp) {
                case -2:
//                    sb.append(TEXT[7]).append(" ");
//                    break;
                case -1:
                    sb.append(TEXT[6]).append(" ");
//                    sb.append(TEXT[6]).append(" ");
                    break;
                case 1:
                case 2:
                    sb.append(TEXT[4]).append(" ");
//                    sb.append(TEXT[4]).append(" ");
                    break;
//                    sb.append(TEXT[5]).append(" ");
//                    break;
            }
        }
//        if (card.hasTag(CustomTags.GUN)) {
//            sb.append(TEXT[3]).append(" ");
//        }
//        if (card.hasTag(CustomTags.EVERBURN)) {
//            sb.append(TEXT[11]).append(" ");
//        }
        if (card.hasTag(CustomTags.FLAMIN)) {
            sb.append(TEXT[11]).append(" ");
        }
//        if (sb.length() != 0) {
//            sb.setLength(sb.length() - 1);
//            sb.append(TEXT[0]);
//        }
        return sb + rawDescription;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PrefixManager();
    }
}