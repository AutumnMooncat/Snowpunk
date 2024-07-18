package Snowpunk.cardmods;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ClankTransformAction;
import Snowpunk.cards.Perfection;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class ClankTransformOnPlay extends AbstractCardModifier implements ClankCardMod {
    public static final String ID = makeID(ClankTransformOnPlay.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;

    public ClankTransformOnPlay() {
        priority = 1;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + Perfection.TEXT[0];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new ClankAction(card, (AbstractMonster) target));
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
        return new ClankTransformOnPlay();
    }

    @Override
    public void onClank(AbstractCard card) {
        addToTop(new ClankTransformAction(card));
    }
}