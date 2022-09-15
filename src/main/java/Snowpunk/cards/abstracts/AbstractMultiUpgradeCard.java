package Snowpunk.cards.abstracts;

import Snowpunk.cardmods.MkMod;
import Snowpunk.cards.interfaces.MultiUpgradeCard;
import Snowpunk.patches.MkPatches;
import basemod.helpers.CardModifierManager;

public abstract class AbstractMultiUpgradeCard extends AbstractEasyCard implements MultiUpgradeCard {

    public AbstractMultiUpgradeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractMultiUpgradeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    @Override
    public void upgrade() {
        processUpgrade();
    }

    @Override
    public void updateName() {
        MkPatches.addMk(this);
    }

    @Override
    public void upp() {}

}
