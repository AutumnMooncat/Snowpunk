package Snowpunk.cards.abstracts;

import Snowpunk.cardmods.MkMod;
import Snowpunk.util.UpgradeRunnable;
import basemod.helpers.CardModifierManager;

import java.util.ArrayList;

public abstract class AbstractMultiUpgradeCard extends AbstractEasyCard {

    protected ArrayList<UpgradeRunnable> upgrades = new ArrayList<>();

    public AbstractMultiUpgradeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        addUpgrades();
    }

    public AbstractMultiUpgradeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        addUpgrades();
    }

    protected abstract void addUpgrades();

    @Override
    public boolean canUpgrade() {
        return !upgrades.isEmpty();
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            upgradeName();
            upp();
            initializeDescription();
        }
    }

    @Override
    public void upp() {
        upgrades.remove(0).doUpgrade();
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        CardModifierManager.addModifier(this, new MkMod(1));
    }
}
