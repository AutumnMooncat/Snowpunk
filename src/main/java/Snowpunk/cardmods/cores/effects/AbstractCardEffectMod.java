package Snowpunk.cardmods.cores.effects;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractCardEffectMod extends AbstractCardModifier {
    public boolean useSecondVar;
    public String description;

    public AbstractCardEffectMod(String description, boolean useSecondVar) {
        this.description = description;
        this.useSecondVar = useSecondVar;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.equals("")) {
            return description;
        } else if (rawDescription.endsWith(" NL ")) {
            return rawDescription + description;
        } else {
            return rawDescription + " NL " + description;
        }
    }
}
