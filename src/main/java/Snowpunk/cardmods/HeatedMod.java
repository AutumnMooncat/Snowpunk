package Snowpunk.cardmods;

import Snowpunk.cards.ChestnutsRoasting;
import Snowpunk.cards.Cryogenizer;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class HeatedMod extends AbstractCardModifier {
    public static final String ID = makeID(HeatedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ChestnutsRoasting.ID).EXTENDED_DESCRIPTION;

    public int amount = 0;

    public HeatedMod(int num) {
        this.priority = 2;
        amount = num;
    }
/*
    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.target == AbstractCard.CardTarget.NONE ||
                card.target == AbstractCard.CardTarget.SELF)
            card.target = AbstractCard.CardTarget.ENEMY;
        if (card.type == AbstractCard.CardType.ATTACK)
            DamageModifierManager.addModifier(card, new ChillDamageMod(amount));
    }*/

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[2] + cardName;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage - amount;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (target instanceof AbstractMonster)
            Wiz.applyToEnemy((AbstractMonster) target, new SingePower(target, amount));
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
            HeatedMod heatMod = (HeatedMod) CardModifierManager.getModifiers(card, ID).get(0);
            heatMod.amount = heatMod.amount + amount;
            if (heatMod.amount < 0)
                heatMod.amount = 0;
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
        return new HeatedMod(amount);
    }
}