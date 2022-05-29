package Snowpunk.cardmods.parts;

import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class DrawAndDiscardRandomMod extends AbstractCardModifier {
    public static final String ID = makeID(DrawAndDiscardRandomMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    int amount;

    public DrawAndDiscardRandomMod(int amount) {
        this.amount = amount;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        StringBuilder sb = new StringBuilder();
        sb.append(rawDescription).append(TEXT[0]).append(amount);
        if (amount == 1) {
            sb.append(TEXT[1]).append(TEXT[3]).append(amount).append(TEXT[4]);
        } else {
            sb.append(TEXT[2]).append(TEXT[3]).append(amount).append(TEXT[5]);
        }
        return sb.toString();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new DrawCardAction(amount));
        Wiz.atb(new DiscardAction(Wiz.adp(), Wiz.adp(), amount, true));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            DrawAndDiscardRandomMod mod = (DrawAndDiscardRandomMod) CardModifierManager.getModifiers(card, ID).get(0);
            mod.amount += amount;
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
        return new DrawAndDiscardRandomMod(amount);
    }
}