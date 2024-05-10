package Snowpunk.cardmods;

import Snowpunk.cards.Cryogenizer;
import Snowpunk.damageMods.ChillDamageMod;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChillMod extends AbstractCardModifier {
    public static final String ID = makeID(ChillMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(Cryogenizer.ID).EXTENDED_DESCRIPTION;

    public int amount = 0;

    public ChillMod(int num) {
        this.priority = 2;
        amount = num;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.target == AbstractCard.CardTarget.NONE ||
                card.target == AbstractCard.CardTarget.SELF)
            card.target = AbstractCard.CardTarget.ENEMY;
        if (card.type == AbstractCard.CardType.ATTACK)
            DamageModifierManager.addModifier(card, new ChillDamageMod(amount));
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK && target instanceof AbstractMonster) {
            Wiz.applyToEnemy((AbstractMonster) target, new ChillPower(target, amount));
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.equals("") || rawDescription.endsWith(" NL "))
            return rawDescription + TEXT[0] + amount + TEXT[1];
        return rawDescription + " NL " + TEXT[0] + amount + TEXT[1];
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            ChillMod chillMod = (ChillMod) CardModifierManager.getModifiers(card, ID).get(0);
            chillMod.amount += amount;
            if (chillMod.amount < 0)
                chillMod.amount = 0;

            if (card.type == AbstractCard.CardType.ATTACK)
                DamageModifierManager.addModifier(card, new ChillDamageMod(amount));

            card.applyPowers();
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
        return new ChillMod(amount);
    }
}