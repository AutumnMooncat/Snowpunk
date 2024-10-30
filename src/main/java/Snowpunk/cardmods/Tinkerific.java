package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class Tinkerific extends AbstractCardModifier {
    public static final String ID = makeID(Tinkerific.class.getSimpleName());

    public Tinkerific() {
        this.priority = -1;
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
        return new Tinkerific();
    }
}