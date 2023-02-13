package Snowpunk.augments;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.cardmods.DynvarCarrier;
import Snowpunk.SnowpunkMod;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class DeckedMod extends AbstractAugment implements DynvarCarrier {
    public static final String ID = SnowpunkMod.makeID(DeckedMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    private static final int EFFECT = 4;
    private static final int UPGRADE_EFFECT = 1;

    public int val;
    public boolean modified;
    public boolean upgraded;

    public int getBaseVal(AbstractCard card) {
        return EFFECT + getEffectiveUpgrades(card) * UPGRADE_EFFECT;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        val = getBaseVal(card);
        card.cost = card.cost + 1;
        card.costForTurn = card.cost;
    }

    @Override
    public void updateDynvar(AbstractCard card) {
        val = getBaseVal(card);
        modified = false;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost >= 0 && card.type != AbstractCard.CardType.POWER && cardCheck(card, c -> doesntUpgradeCost());
    }

    @Override
    public String getPrefix() {
        return TEXT[0];
    }

    @Override
    public String getSufix() {
        return TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[2], DESCRIPTION_KEY);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.applyToSelf(new SparePartsPower(Wiz.adp(), getBaseVal(card)));
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.COMMON;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DeckedMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String key() {
        return ID;
    }

    @Override
    public int val(AbstractCard card) {
        return val;
    }

    @Override
    public int baseVal(AbstractCard card) {
        return getBaseVal(card);
    }

    @Override
    public boolean modified(AbstractCard card) {
        return modified;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        val = getBaseVal(card);
        modified = card.upgraded;
        upgraded = card.upgraded;
        return upgraded;
    }
}
