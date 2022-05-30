package Snowpunk.cardmods.cores;

import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DealDamageMod extends AbstractCoreCardMod {
    int damage;
    int upDamage;

    public DealDamageMod(String name, String description, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int damage, int upDamage) {
        super(name, description, cost, type, rarity, target);
        this.damage = damage;
        this.upDamage = upDamage;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateDamage(card, damage, upDamage);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new DamageAction(target, new DamageInfo(Wiz.adp(), card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealDamageMod(name, description, cost, type, rarity, target, damage, upDamage);
    }
}
