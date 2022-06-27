package Snowpunk.cards.interfaces;

import Snowpunk.patches.MultiUpgradePatches;
import Snowpunk.util.UpgradeData;
import Snowpunk.util.UpgradeRunnable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public interface MultiUpgradeCard {
    void addUpgrades();

    void updateName();

    default ArrayList<UpgradeData> getUpgrades(AbstractCard card) {
        return MultiUpgradePatches.MultiUpgradeFields.upgrades.get(card);
    }

    default int upgradeLimit() {
        return -1;
    }

    default boolean canPerformUpgrade(AbstractCard card) {
        boolean hasUpgrade = getUpgrades(card).stream().anyMatch(u -> u.canUpgrade(getUpgrades(card)));
        if (!hasUpgrade) {
            return false;
        }
        if (upgradeLimit() != -1) {
            return getUpgrades(card).stream().filter(u -> u.applied).count() < upgradeLimit();
        }
        return true;
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, int... dependencies) {
        MultiUpgradePatches.MultiUpgradeFields.upgrades.get(card).add(new UpgradeData(r, getUpgrades(card).size(), dependencies));
    }

    default void processUpgrade(AbstractCard card) {
        //Get the upgrades
        ArrayList<UpgradeData> upgrades = getUpgrades(card);
        //Get the next upgrade index
        int i = MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(card);
        //If it is -1, then this means grab the next available upgrade
        if (i == -1) {
            for (UpgradeData d : upgrades) {
                if (!d.applied && d.canUpgrade(upgrades)) {
                    i = (upgrades.indexOf(d));
                    break;
                }
            }
        }

        //If we can perform the upgrade, do it
        if (i != -1 && upgrades.size() > i && !upgrades.get(i).applied && upgrades.get(i).canUpgrade(upgrades)) {
            upgrades.get(i).upgrade();
            card.timesUpgraded += (1 << i);
            card.upgraded = true;
            updateName();
            card.initializeDescription();
        }

        //Default back to the next upgrade
        MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(card, -1);
    }
}
