package Snowpunk.cardmods;

import Snowpunk.cardmods.parts.GainEnergyMod;
import Snowpunk.patches.CustomTags;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class MkMod extends AbstractCardModifier {
    public static final String ID = makeID(MkMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount;

    public MkMod(int amount) {
        this.priority = 3;
        this.amount = amount;
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return cardName + TEXT[0] + amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            MkMod mk = (MkMod) CardModifierManager.getModifiers(card, ID).get(0);
            mk.amount += amount;
            return false;
        }
        return true;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MkMod(amount);
    }
}