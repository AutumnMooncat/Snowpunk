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
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static Snowpunk.SnowpunkMod.makeID;

public class FastMod extends AbstractCardModifier {
    public static final String ID = makeID(FastMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    int amount;

    public FastMod(int amount) {
        this.amount = amount;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (amount == 1)
            return rawDescription + TEXT[0];
        StringBuilder sb = new StringBuilder();
        if (amount > 3)
            sb.append(rawDescription).append(TEXT[1]).append(TEXT[5]).append(amount).append(TEXT[2]).append(TEXT[3]).append(amount).append(TEXT[4]);
        else {
            sb.append(rawDescription).append(TEXT[1]);
            for (int i = 0; i < amount; i++) {
                sb.append(TEXT[2]);
            }
            sb.append(TEXT[3]).append(amount).append(TEXT[4]);
        }
        return sb.toString();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractPlayer player = AbstractDungeon.player;
        Wiz.applyToSelf(new EnergizedPower(player, amount));
        Wiz.applyToSelf(new DrawCardNextTurnPower(player, amount));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            FastMod mod = (FastMod) CardModifierManager.getModifiers(card, ID).get(0);
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
        return new FastMod(amount);
    }
}