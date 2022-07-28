package Snowpunk.cards.interfaces;

import Snowpunk.patches.MultiUpgradePatches;
import Snowpunk.util.UpgradeData;
import Snowpunk.util.UpgradeRunnable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.stream.Collectors;

public interface MultiUpgradeCard {
    enum TreeStyle {
        DEFAULT_TREE,
        FORCE_DIRECTED_TREE
    }

    void addUpgrades();

    void updateName();

    default TreeStyle getTreeStyle() {
        return TreeStyle.DEFAULT_TREE;
    }

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

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, boolean strict, int... dependencies) {
        MultiUpgradePatches.MultiUpgradeFields.upgrades.get(card).add(new UpgradeData(r, getUpgrades(card).size(), strict, dependencies));
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, boolean strict, int[] dependencies, int[] exclusions) {
        MultiUpgradePatches.MultiUpgradeFields.upgrades.get(card).add(new UpgradeData(r, getUpgrades(card).size(), strict, dependencies, exclusions));
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, AbstractCard alias, int[] dependencies, boolean strict, int[] exclusions) {
        MultiUpgradePatches.MultiUpgradeFields.upgrades.get(card).add(new UpgradeData(r, getUpgrades(card).size(), alias, dependencies, strict, exclusions));
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, String name, String description, int... dependencies) {
        MultiUpgradePatches.MultiUpgradeFields.upgrades.get(card).add(new UpgradeData(r, getUpgrades(card).size(), name, description, dependencies));
    }

    default void processUpgrade(AbstractCard card) {
        //Get the upgrades
        ArrayList<UpgradeData> upgrades = getUpgrades(card);
        //Get the next upgrade index
        int i = MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(card);
        //If it is -1, then this means grab a random available upgrade
        if (i == -1) {
            ArrayList<UpgradeData> validUpgrades = upgrades.stream().filter(u -> !u.applied && u.canUpgrade(upgrades)).collect(Collectors.toCollection(ArrayList::new));
            if (!validUpgrades.isEmpty()) {
                if (AbstractDungeon.cardRandomRng == null || AbstractDungeon.player == null) {
                    i = validUpgrades.get(0).index;
                } else {
                    i = validUpgrades.get(AbstractDungeon.cardRandomRng.random(validUpgrades.size()-1)).index;
                }

            }
        }

        //If we can perform the upgrade, do it
        if (i != -1 && upgrades.size() > i && !upgrades.get(i).applied /*&& upgrades.get(i).canUpgrade(upgrades)*/) {
            updateName();
            card.timesUpgraded += (1 << i);
            card.upgraded = true;
            upgrades.get(i).upgrade();
            //card.initializeDescription();
        }

        //Default back to a random upgrade
        MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(card, -1);
    }

}
