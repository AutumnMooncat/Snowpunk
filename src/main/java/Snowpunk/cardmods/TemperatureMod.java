package Snowpunk.cardmods;

import Snowpunk.actions.ExhumeMostRecentAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TemperatureMod extends AbstractCardModifier {
    public static String ID = makeID(TemperatureMod.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    public TemperatureMod() {}

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        switch (CardTemperatureFields.getCardHeat(card)) {
            case -2:
                return TEXT[3] + rawDescription;
            case -1:
                return TEXT[2] + rawDescription;
            case 1:
                return TEXT[0] + rawDescription;
            case 2 :
                return TEXT[1] + rawDescription;
        }
        return rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int heat = CardTemperatureFields.getCardHeat(card);
        Wiz.atb(new ModEngineTempAction(heat));
        if (heat > 0) {
            action.exhaustCard = true;
            Wiz.atb(new GainEnergyAction(1));
        }
        if (heat == 2) {
            card.use(Wiz.adp(), (AbstractMonster) target);
        }
        if (heat < 0) {
            Wiz.atb(new DrawCardAction(1));
        }
        if (heat == -2) {
            Wiz.atb(new ExhumeMostRecentAction(card, 1));
        }
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        if (!card.isEthereal && CardTemperatureFields.getCardHeat(card) < 0) {
            card.retain = true;
        }
        return false;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
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
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new TemperatureMod();
    }
}
