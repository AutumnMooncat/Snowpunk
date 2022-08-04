package Snowpunk.powers;

import Snowpunk.cardmods.FrostMod;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.interfaces.OnCreateCardPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class TheSnowmanPower extends AbstractEasyPower implements OnCreateCardPower {
    public static String POWER_ID = makeID(TheSnowmanPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public TheSnowmanPower(AbstractCreature owner) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, -1);
        this.loadRegion("hymn");
    }

    @Override
    public void onInitialApplication() {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(Wiz.adp().hand.group);
        allCards.addAll(Wiz.adp().drawPile.group);
        allCards.addAll(Wiz.adp().discardPile.group);
        allCards.addAll(Wiz.adp().exhaustPile.group);
        allCards.addAll(Wiz.adp().limbo.group);
        allCards.addAll(EvaporatePanel.evaporatePile.group);
        for (AbstractCard card : allCards) {
            if (!card.hasTag(CustomTags.FROSTY)) {
                CardModifierManager.addModifier(card, new FrostMod());
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        if (!card.hasTag(CustomTags.FROSTY)) {
            CardModifierManager.addModifier(card, new FrostMod());
        }
    }
}
