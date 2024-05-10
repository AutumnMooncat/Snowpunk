package Snowpunk.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveDamageModifierAction extends AbstractGameAction {
    private AbstractDamageModifier mod;
    private AbstractCard card;

    public RemoveDamageModifierAction(AbstractCard card, AbstractDamageModifier mod) {
        this.mod = mod;
        this.card = card;
    }

    @Override
    public void update() {
        DamageModifierManager.removeModifier(card, mod);
        isDone = true;
    }
}
