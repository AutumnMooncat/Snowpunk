package Snowpunk.cardmods.cores.effects;

import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractCardEffectMod extends AbstractCardModifier {
    public AbstractCoreCard.ValueType type;
    public boolean useSecondVar;
    public String description;
    public int effect;
    public int upEffect;

    public AbstractCardEffectMod(String description, AbstractCoreCard.ValueType type, int effect, int upEffect, boolean useSecondVar) {
        this.description = description;
        this.type = type;
        this.effect = effect;
        this.upEffect = upEffect;
        this.useSecondVar = useSecondVar;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card instanceof AssembledCard) {
            switch (type) {
                case DAMAGE:
                    setUpDamage((AssembledCard) card, effect, upEffect, useSecondVar);
                    break;
                case BLOCK:
                    setUpBlock((AssembledCard) card, effect, upEffect, useSecondVar);
                    break;
                case MAGIC:
                    setUpMagic((AssembledCard) card, effect, upEffect, useSecondVar);
                    break;
                case NONE:
                    break;
            }
        }
    }

    protected void setUpDamage(AssembledCard card, int effect, int upEffect, boolean useSecondVar) {
        if (useSecondVar) {
            card.baseSecondDamage = effect;
            if (card.upgraded) {
                card.baseSecondDamage += upEffect;
            } else {
                card.addUpgradeConsumer(c -> {
                    c.baseSecondDamage += upEffect;
                    c.secondDamage = c.baseSecondDamage;
                    c.upgradedSecondDamage = true;
                });
            }
            card.secondDamage = card.baseSecondDamage;
        } else {
            card.baseDamage = effect;
            if (card.upgraded) {
                card.baseDamage += upEffect;
            } else {
                card.addUpgradeConsumer(c -> {
                    c.baseDamage += upEffect;
                    c.damage = c.baseDamage;
                    c.upgradedDamage = true;
                });
            }
            card.damage = card.baseDamage;
        }
    }

    protected void setUpBlock(AssembledCard card, int effect, int upEffect, boolean useSecondVar) {
        if (useSecondVar) {
            card.baseSecondBlock = effect;
            if (card.upgraded) {
                card.baseSecondBlock += upEffect;
            } else {
                card.addUpgradeConsumer(c -> {
                    c.baseSecondBlock += upEffect;
                    c.secondBlock = c.baseSecondBlock;
                    c.upgradedSecondBlock = true;
                });
            }
            card.secondBlock = card.baseSecondBlock;
        } else {
            card.baseBlock = effect;
            if (card.upgraded) {
                card.baseBlock += upEffect;
            } else {
                card.addUpgradeConsumer(c -> {
                    c.baseBlock += upEffect;
                    c.block = c.baseBlock;
                    c.upgradedBlock = true;
                });
            }
            card.block = card.baseBlock;
        }
    }

    protected void setUpMagic(AssembledCard card, int effect, int upEffect, boolean useSecondVar) {
        if (useSecondVar) {
            card.baseSecondMagic = effect;
            if (card.upgraded) {
                card.baseSecondMagic += upEffect;
            } else {
                card.addUpgradeConsumer(c -> {
                    c.baseSecondMagic += upEffect;
                    c.secondMagic = c.baseSecondMagic;
                    c.upgradedSecondMagic = true;
                });
            }
            card.secondMagic = card.baseSecondMagic;
        } else {
            card.baseMagicNumber = effect;
            if (card.upgraded) {
                card.baseMagicNumber += upEffect;
            } else {
                card.addUpgradeConsumer(c -> {
                    c.baseMagicNumber += upEffect;
                    c.magicNumber = c.baseMagicNumber;
                    c.upgradedMagicNumber = true;
                });
            }
            card.magicNumber = card.baseMagicNumber;
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.equals("")) {
            return description;
        } else {
            return rawDescription + " NL " + description;
        }
    }
}
