package Snowpunk.cardmods.cores.edits;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.AssembledCardArtRoller;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Snowpunk.SnowpunkMod.makeID;

public class CardEditMod extends AbstractCardModifier {
    public static final String ID = makeID(CardEditMod.class.getSimpleName());
    public String name;
    public int cost;
    public AbstractCard.CardType type;
    public AbstractCard.CardRarity rarity;
    public AbstractCard.CardTarget target;

    @Deprecated
    public CardEditMod(String name, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this.name = name;
        this.cost = 1;
        this.type = type;
        this.rarity = rarity;
        this.target = target;
    }

    public CardEditMod(String name, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.rarity = rarity;
        this.target = target;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card, name, cost, type, rarity, target);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)){
            CardEditMod mod = (CardEditMod) CardModifierManager.getModifiers(card, ID).get(0);
            mod.collateCardBasics(card, name, cost, type, rarity, target);
            return false;
        }
        return true;
    }

    public void collateCardBasics(AbstractCard card, String name, int cost, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        collateName(card, name);
        collateCost(card, cost);
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
        this.name = card.name;
    }

    public void collateCost(AbstractCard card, int cost) {
        if (card.cost == -1) {
            return;
        }
        if (cost == -1) {
            card.cost = -1;
            card.costForTurn = -1;
        } else {
            card.cost += cost;
            card.costForTurn = card.cost;
        }
        this.cost = card.cost;
    }

    public void collateType(AbstractCard card, AbstractCard.CardType type) {
        if (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.POWER) {
            return;
        }
        card.type = type;
        this.type = card.type;
    }

    public void collateRarity(AbstractCard card, AbstractCard.CardRarity rarity) {
        if (card.rarity.ordinal() < rarity.ordinal()) {
            card.rarity = rarity;
        }
        this.rarity = card.rarity;
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
        this.target = card.target;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardEditMod(name, cost, type, rarity, target);
    }
}
