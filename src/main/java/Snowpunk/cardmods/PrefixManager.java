package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.EvaporatePanelPatches;
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
        if (card.hasTag(CustomTags.FLUX)) {
            sb.append(TEXT[1]).append(" ");
        }
        int temp = CardTemperatureFields.getCardHeat(card);
        if (temp != 0) {
            if (card.hasTag(CustomTags.FLUX)) {
                if (temp == 1 || temp == -1) {
                    sb.append(TEXT[8]).append(" ");
                } else {
                    sb.append(TEXT[9]).append(" ");
                }
            } else {
                switch (temp) {
                    case -2:
                        sb.append(TEXT[7]).append(" ");
                        break;
                    case -1:
                        sb.append(TEXT[6]).append(" ");
                        break;
                    case 1:
                        sb.append(TEXT[4]).append(" ");
                        break;
                    case 2 :
                        sb.append(TEXT[5]).append(" ");
                        break;
                }
                //addTempKeywords(card, temp);
            }
        }
        if (card.hasTag(CustomTags.FROSTY)) {
            sb.append(TEXT[2]).append(" ");
        }
        if (card.hasTag(CustomTags.GUN)) {
            sb.append(TEXT[3]).append(" ");
        }
        if (sb.length() != 0) {
            sb.setLength(sb.length() - 1);
            sb.append(TEXT[0]);
        }
        return sb + rawDescription;
    }

    private void addTempKeywords(AbstractCard card, int temp) {
        if (temp < 0) {
            if (!card.keywords.toString().contains(KeywordManager.CONDENSE))
                card.keywords.add(0, KeywordManager.CONDENSE);
            if (card.keywords.toString().contains(KeywordManager.EVAPORATE) && !card.description.contains(KeywordManager.EVAPORATE))
                card.keywords.remove(KeywordManager.EVAPORATE);
        } else if (temp > 0) {
            if (!card.keywords.toString().contains(KeywordManager.EVAPORATE))
                card.keywords.add(0, KeywordManager.EVAPORATE);
            if (card.keywords.toString().contains(KeywordManager.CONDENSE) && !card.description.contains(KeywordManager.CONDENSE))
                card.keywords.remove(KeywordManager.CONDENSE);
        } else {
            if (card.keywords.toString().contains(KeywordManager.EVAPORATE) && !card.description.contains(KeywordManager.EVAPORATE))
                card.keywords.remove(KeywordManager.EVAPORATE);
            if (card.keywords.toString().contains(KeywordManager.CONDENSE) && !card.description.contains(KeywordManager.CONDENSE))
                card.keywords.remove(KeywordManager.CONDENSE);
        }
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