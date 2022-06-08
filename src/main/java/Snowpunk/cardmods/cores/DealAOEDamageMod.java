package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class DealAOEDamageMod extends AbstractCoreCardMod {
    int damage;
    int upDamage;

    public DealAOEDamageMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int damage, int upDamage) {
        super(name, description, type, rarity, target);
        this.damage = damage;
        this.upDamage = upDamage;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateDamage(card, damage, upDamage);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseConsumer((p, m) -> {
                if (useSecondVar) {
                    Wiz.atb(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(((AssembledCard) card).baseSecondDamage), card.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
                } else {
                    Wiz.atb(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(card.baseDamage), card.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealAOEDamageMod(name, description, type, rarity, target, damage, upDamage);
    }
}
