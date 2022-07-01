package Snowpunk.cardmods.parts;

import Snowpunk.powers.TinkerNextCardPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ThornsMod extends AbstractCardModifier {
    public static final String ID = makeID(ThornsMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    int amount;

    public ThornsMod(int amount) {
        this.amount = amount;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        StringBuilder sb = new StringBuilder();
        sb.append(rawDescription).append(TEXT[0]).append(amount).append(TEXT[1]);
        return sb.toString();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractPlayer player = AbstractDungeon.player;
        Wiz.applyToSelf(new ThornsPower(player, amount));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            ThornsMod mod = (ThornsMod) CardModifierManager.getModifiers(card, ID).get(0);
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
        return new ThornsMod(amount);
    }
}