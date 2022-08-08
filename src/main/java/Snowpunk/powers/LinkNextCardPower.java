package Snowpunk.powers;

import Snowpunk.cardmods.LinkMod;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class LinkNextCardPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(LinkNextCardPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private AbstractCard cardToLink;

    public LinkNextCardPower(AbstractCreature owner, int amount, AbstractCard cardToLink) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.cardToLink = cardToLink;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && (!CardModifierManager.hasModifier(card, LinkMod.ID) ||
                (CardModifierManager.hasModifier(card, LinkMod.ID) &&
                        !((LinkMod) CardModifierManager.getModifiers(card, LinkMod.ID).get(0)).linkedCards.contains(cardToLink)
                ))) {
            flash();
            LinkMod.Link(card, cardToLink);
            LinkMod.Link(cardToLink, card);
            this.addToTop(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + (cardToLink == null ? "" : cardToLink.name) + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + (cardToLink == null ? "" : cardToLink.name) + DESCRIPTIONS[4];
        }
    }
}
