package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class DealDamageTwiceMod extends AbstractCoreCardMod {
    int damage;
    int upDamage;

    public DealDamageTwiceMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int damage, int upDamage) {
        super(name, description, type, rarity, target);
        this.damage = damage;
        this.upDamage = upDamage;
        this.priority = -1;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateDamage(card, damage, upDamage);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseConsumer((p, m) -> {
                if (useSecondVar) {
                    Wiz.atb(new DamageAction(m, new DamageInfo(p, ((AssembledCard) card).secondDamage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    Wiz.atb(new DamageAction(m, new DamageInfo(p, ((AssembledCard) card).secondDamage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                } else {
                    Wiz.atb(new DamageAction(m, new DamageInfo(p, card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    Wiz.atb(new DamageAction(m, new DamageInfo(p, card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealDamageTwiceMod(name, description, type, rarity, target, damage, upDamage);
    }
}
