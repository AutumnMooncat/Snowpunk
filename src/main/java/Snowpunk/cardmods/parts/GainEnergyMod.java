package Snowpunk.cardmods.parts;

import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class GainEnergyMod extends AbstractCardModifier {
    public static final String ID = makeID(GainEnergyMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    int amount;

    public GainEnergyMod(int amount) {
        this.amount = amount;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        StringBuilder sb = new StringBuilder();
        /*sb.append(rawDescription).append(TEXT[0]);
        for (int i = 0 ; i < amount ; i++) {
            sb.append(TEXT[1]);
        }
        sb.append(TEXT[2]);*/
        sb.append(rawDescription).append(TEXT[3]);
        if (amount > 1)
            sb.append(TEXT[4]).append(amount);
        return sb.toString();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new GainEnergyAction(amount));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            GainEnergyMod gem = (GainEnergyMod) CardModifierManager.getModifiers(card, ID).get(0);
            gem.amount += amount;
            card.initializeDescription();
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
        return new GainEnergyMod(amount);
    }
}