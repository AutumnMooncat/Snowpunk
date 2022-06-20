package Snowpunk.cardmods.cores.edits;

import Snowpunk.cardmods.BetterExhaustMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.AssembledCardArtRoller;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardEditMod extends AbstractCardModifier {
    public String name;
    public int doubledCost;
    public AbstractCard.CardType type;
    public AbstractCard.CardRarity rarity;
    public AbstractCard.CardTarget target;

    public CardEditMod(String name, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this.name = name;
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

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
    }

    public void collateCardBasics(AbstractCard card) {
        collateName(card, name);
        collateCost(card, doubledCost);
        collateType(card, type);
        collateRarity(card, rarity);
        collateTarget(card, target);
        if (card instanceof AbstractEasyCard) {
            AssembledCardArtRoller.computeCard((AbstractEasyCard) card);
        }
    }

    public void collateName(AbstractCard card, String name) {
        boolean rippedPlus = false;
        if (card.name.endsWith("+")){
            card.name = card.name.substring(0, card.name.length()-1);
            rippedPlus = true;
        }
        if (card.name.equals("")) {
            card.name = name;
        } else if (!name.equals("")) {
            card.name += " " + name;
        }
        if (rippedPlus) {
            card.name += "+";
        }
    }

    public void collateCost(AbstractCard card, int doubledCost) {
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).doubledCost += doubledCost;
            card.cost = card.costForTurn = (int) Math.ceil(((AssembledCard) card).doubledCost/2f);
        }
    }

    public void collateType(AbstractCard card, AbstractCard.CardType type) {
        if (card.rarity == AbstractCard.CardRarity.SPECIAL) {
            card.type = type;
            return;
        }
        if (type == AbstractCard.CardType.POWER && card.type != AbstractCard.CardType.POWER) {
            CardModifierManager.addModifier(card, new BetterExhaustMod());
            return;
        }
        if (card.type == AbstractCard.CardType.POWER) {
            CardModifierManager.addModifier(card, new BetterExhaustMod());
        }
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

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardEditMod(name, type, rarity, target);
    }
}
