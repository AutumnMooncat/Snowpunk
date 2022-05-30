package Snowpunk.cardmods.cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.AssembledCardArtRoller;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractCoreCardMod extends AbstractCardModifier {
    public String name;
    public String description;
    public int cost;
    public AbstractCard.CardType type;
    public AbstractCard.CardRarity rarity;
    public AbstractCard.CardTarget target;

    public AbstractCoreCardMod(String name, String description, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.rarity = rarity;
        this.target = target;
    }

    public void collateCardBasics(AbstractCard card) {
        collateName(card, name);
        collateDescription(card, description);
        collateCost(card, cost);
        collateType(card, type);
        collateRarity(card, rarity);
        collateTarget(card, target);
        if (card instanceof AbstractEasyCard) {
            AssembledCardArtRoller.computeCard((AbstractEasyCard) card);
        }
    }

    public void collateName(AbstractCard card, String name) {
        if (card.name.equals("")) {
            card.name = name;
        } else if (!name.equals("")) {
            card.name += " " + name;
        }
    }

    public void collateDescription(AbstractCard card, String description) {
        if (card.rawDescription.equals("")) {
            card.rawDescription = description;
        } else if (!description.equals("")){
            card.rawDescription += " NL " + description;
        }
    }

    public void collateCost(AbstractCard card, int cost) {
        //Collate positive cost values
        if (cost > 0 && card.cost >= 0) {
            card.cost += cost;
            card.costForTurn = card.cost;
        } else if (cost < 0) {
            //For X or Unplayable, just override the cost entirely, as they take precedence
            card.cost = cost;
            card.costForTurn = cost;
        }
    }

    public void collateDamage(AbstractCard card, int damage, int upDamage) {
        if (card.baseDamage == -1) {
            card.baseDamage = 0;
        }
        if (card.upgraded) {
            card.baseDamage += damage + upDamage;
        } else {
            card.baseDamage = damage;
            if (card instanceof AssembledCard) {
                ((AssembledCard) card).addUpgradeFunction(ac -> {
                    ac.baseDamage += upDamage;
                    ac.upgradedDamage = true;
                    return null;
                });
            }
        }
        card.damage = card.baseDamage;
    }

    public void collateBlock(AbstractCard card, int block, int upBlock) {
        if (card.baseBlock == -1) {
            card.baseBlock = 0;
        }
        if (card.upgraded) {
            card.baseBlock += block + upBlock;
        } else {
            card.baseBlock = block;
            if (card instanceof AssembledCard) {
                ((AssembledCard) card).addUpgradeFunction(ac -> {
                    ac.baseBlock += upBlock;
                    ac.upgradedBlock = true;
                    return null;
                });
            }
        }
        card.block = card.baseBlock;
    }

    public void collateMagic(AbstractCard card, int magic, int upMagic) {
        if (card.baseMagicNumber == -1) {
            card.baseMagicNumber = 0;
        }
        if (card.upgraded) {
            card.baseMagicNumber += magic + upMagic;
        } else {
            card.baseMagicNumber = magic;
            if (card instanceof AssembledCard) {
                ((AssembledCard) card).addUpgradeFunction(ac -> {
                    ac.baseMagicNumber += upMagic;
                    ac.upgradedBlock = true;
                    return null;
                });
            }
        }
        card.magicNumber = card.baseMagicNumber;
    }

    public void collateType(AbstractCard card, AbstractCard.CardType type) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            return;
        }
        card.type = type;
    }

    public void collateRarity(AbstractCard card, AbstractCard.CardRarity rarity) {
        if (card.rarity.ordinal() < rarity.ordinal()) {
            card.rarity = rarity;
        }
    }

    public void collateTarget(AbstractCard card, AbstractCard.CardTarget target) {
        if (card.target == target || target == AbstractCard.CardTarget.NONE) {
            return;
        } else if (card.target == AbstractCard.CardTarget.NONE) {
            card.target = target;
        }

        if (target == AbstractCard.CardTarget.SELF_AND_ENEMY) {
            card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
        } else if (target == AbstractCard.CardTarget.ENEMY && card.target != AbstractCard.CardTarget.SELF_AND_ENEMY) {
            card.target = AbstractCard.CardTarget.ENEMY;
        } else {
            switch (card.target) {
                case ALL_ENEMY:
                    if (target == AbstractCard.CardTarget.SELF) {
                        card.target = AbstractCard.CardTarget.ALL;
                    }
                    break;
                case SELF:
                    if (target == AbstractCard.CardTarget.ALL_ENEMY) {
                        card.target = AbstractCard.CardTarget.ALL;
                    }
                    break;
            }
        }
    }
}

