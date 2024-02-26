package Snowpunk.cardmods;

import Snowpunk.patches.CustomTags;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class DesperationMod extends AbstractCardModifier implements FreeToPlayMod {
    public static final String ID = makeID(DesperationMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public DesperationMod() {
        this.priority = 1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

    }

    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        if (Wiz.adp() == null)
            return false;
        int numDebuffs = 0;
        for (AbstractPower power : Wiz.adp().powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF)
                numDebuffs++;
        }
        return Wiz.adp().isBloodied || numDebuffs >= 2;
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
        return new DesperationMod();
    }
}