package Snowpunk.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.SnowpunkMod.makeID;

public class DontIncludeInCombatMod extends AbstractCardModifier {
    public static final String ID = makeID(DontIncludeInCombatMod.class.getSimpleName());

    public DontIncludeInCombatMod() {
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
        return new DontIncludeInCombatMod();
    }
}