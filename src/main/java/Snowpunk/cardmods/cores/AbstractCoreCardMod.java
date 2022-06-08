package Snowpunk.cardmods.cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.AssembledCardArtRoller;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractCoreCardMod extends AbstractCardModifier {
    public String name;
    public String description;
    public int doubledCost;
    public AbstractCard.CardType type;
    public AbstractCard.CardRarity rarity;
    public AbstractCard.CardTarget target;
    public boolean useSecondVar;

    public AbstractCoreCardMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this.name = name;
        this.description = description;
        this.doubledCost = getDoubledCostFromRarity(rarity);
        this.type = type;
        this.rarity = rarity;
        this.target = target;
    }

    protected int getDoubledCostFromRarity(AbstractCard.CardRarity rarity) {
        if (rarity == AbstractCard.CardRarity.RARE) {
            return 3;
        } else if (rarity == AbstractCard.CardRarity.UNCOMMON) {
            return 2;
        } else {
            return 1;
        }
    }

    public void collateCardBasics(AbstractCard card) {
        collateName(card, name);
        collateDescription(card, description);
        collateCost(card, doubledCost);
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

    public void collateCost(AbstractCard card, int doubledCost) {
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).doubledCost += doubledCost;
            card.cost = card.costForTurn = (int) Math.ceil(((AssembledCard) card).doubledCost/2f);
        }
    }

    public void collateDamage(AbstractCard card, int damage, int upDamage) {
        if (card.baseDamage == -1) {
            card.baseDamage = 0;
            if (card.upgraded) {
                card.baseDamage += damage + upDamage;
            } else {
                card.baseDamage += damage;
                if (card instanceof AssembledCard) {
                    ((AssembledCard) card).addUpgradeConsumer(ac -> {
                        ac.baseDamage += upDamage;
                        ac.damage = ac.baseDamage;
                        ac.upgradedDamage = true;
                    });
                }
            }
            card.damage = card.baseDamage;
        } else if (card instanceof AbstractEasyCard) {
            if (((AbstractEasyCard) card).baseSecondDamage == -1) {
                useSecondVar = true;
                ((AbstractEasyCard) card).baseSecondDamage = 0;
                if (card.upgraded) {
                    ((AbstractEasyCard) card).baseSecondDamage += damage + upDamage;
                } else {
                    ((AbstractEasyCard) card).baseSecondDamage += damage;
                    if (card instanceof AssembledCard) {
                        ((AssembledCard) card).addUpgradeConsumer(ac -> {
                            if (ac instanceof AbstractEasyCard) {
                                ((AbstractEasyCard) ac).baseSecondDamage += upDamage;
                                ((AbstractEasyCard) ac).secondDamage = ((AbstractEasyCard) ac).baseSecondDamage;
                                ((AbstractEasyCard) ac).upgradedSecondDamage = true;
                            }
                        });
                    }
                }
                ((AbstractEasyCard) card).secondDamage = ((AbstractEasyCard) card).baseSecondDamage;
            }
        }
    }

    public void collateBlock(AbstractCard card, int block, int upBlock) {
        if (card.baseBlock == -1) {
            card.baseBlock = 0;
            if (card.upgraded) {
                card.baseBlock += block + upBlock;
            } else {
                card.baseBlock += block;
                if (card instanceof AssembledCard) {
                    ((AssembledCard) card).addUpgradeConsumer(ac -> {
                        ac.baseBlock += upBlock;
                        ac.block = ac.baseBlock;
                        ac.upgradedBlock = true;
                    });
                }
            }
            card.block = card.baseBlock;
        } else if (card instanceof AbstractEasyCard) {
            if (((AbstractEasyCard) card).baseSecondBlock == -1) {
                useSecondVar = true;
                ((AbstractEasyCard) card).baseSecondBlock = 0;
                if (card.upgraded) {
                    ((AbstractEasyCard) card).baseSecondBlock += block + upBlock;
                } else {
                    ((AbstractEasyCard) card).baseSecondBlock += block;
                    if (card instanceof AssembledCard) {
                        ((AssembledCard) card).addUpgradeConsumer(ac -> {
                            if (ac instanceof AbstractEasyCard) {
                                ((AbstractEasyCard) ac).baseSecondBlock += upBlock;
                                ((AbstractEasyCard) ac).secondBlock = ((AbstractEasyCard) ac).baseSecondBlock;
                                ((AbstractEasyCard) ac).upgradedSecondBlock = true;
                            }
                        });
                    }
                }
                ((AbstractEasyCard) card).secondBlock = ((AbstractEasyCard) card).baseSecondBlock;
            }
        }
    }

    public void collateMagic(AbstractCard card, int magic, int upMagic) {
        if (card.baseMagicNumber == -1) {
            card.baseMagicNumber = 0;
            if (card.upgraded) {
                card.baseMagicNumber += magic + upMagic;
            } else {
                card.baseMagicNumber += magic;
                if (card instanceof AssembledCard) {
                    ((AssembledCard) card).addUpgradeConsumer(ac -> {
                        ac.baseMagicNumber += upMagic;
                        ac.magicNumber = ac.baseMagicNumber;
                        ac.upgradedBlock = true;
                    });
                }
            }
            card.magicNumber = card.baseMagicNumber;
        } else if (card instanceof AbstractEasyCard) {
            if (((AbstractEasyCard) card).baseSecondMagic == -1) {
                useSecondVar = true;
                ((AbstractEasyCard) card).baseSecondMagic = 0;
                if (card.upgraded) {
                    ((AbstractEasyCard) card).baseSecondMagic += magic + upMagic;
                } else {
                    ((AbstractEasyCard) card).baseSecondMagic += magic;
                    if (card instanceof AssembledCard) {
                        ((AssembledCard) card).addUpgradeConsumer(ac -> {
                            if (ac instanceof AbstractEasyCard) {
                                ((AbstractEasyCard) ac).baseSecondMagic += upMagic;
                                ((AbstractEasyCard) ac).secondMagic = ((AbstractEasyCard) ac).baseSecondMagic;
                                ((AbstractEasyCard) ac).upgradedSecondMagic = true;
                            }
                        });
                    }
                }
                ((AbstractEasyCard) card).secondMagic = ((AbstractEasyCard) card).baseSecondMagic;
            }
        }
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

