package Snowpunk.cardmods.parts;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.damageMods.BurnDamage;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

public class BurnDamageMod extends AbstractCardModifier {
    public static final String ID = makeID(BurnDamageMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    int amount;

    public BurnDamageMod(int amount) {
        this.amount = amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new BurnDamage(amount));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + String.format(TEXT[0], amount);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            BurnDamageMod mod = (BurnDamageMod) CardModifierManager.getModifiers(card, ID).get(0);
            mod.amount += amount;
            for (AbstractDamageModifier dm : DamageModifierManager.modifiers(card)) {
                if (dm instanceof BurnDamage) {
                    ((BurnDamage) dm).amount += amount;
                    break;
                }
            }
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
        return new BurnDamageMod(amount);
    }
}