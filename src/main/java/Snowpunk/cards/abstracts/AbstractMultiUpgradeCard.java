package Snowpunk.cards.abstracts;

import Snowpunk.patches.MkPatches;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard;

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
