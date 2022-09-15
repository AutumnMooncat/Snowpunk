package Snowpunk.cards.interfaces;

import Snowpunk.patches.MultiUpgradePatches;
import Snowpunk.util.UpgradeData;
import Snowpunk.util.UpgradeRunnable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

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

    default ArrayList<UpgradeData> getUpgrades() {
        return MultiUpgradePatches.MultiUpgradeFields.upgrades.get(this);
    }

    default int upgradeLimit() {
        return -1;
    }

    default boolean canPerformUpgrade() {
        boolean hasUpgrade = getUpgrades().stream().anyMatch(u -> u.canUpgrade(getUpgrades()));
        if (!hasUpgrade) {
            return false;
        }
        if (upgradeLimit() != -1) {
            return getUpgrades().stream().filter(u -> u.applied).count() < upgradeLimit();
        }
        return true;
    }

    default int upgradesPerformed(AbstractCard card) {
        return (int) getUpgrades().stream().filter(u -> u.applied).count();
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, int... dependencies) {
        getUpgrades().add(new UpgradeData(r, getUpgrades().size(), dependencies));
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, int[] dependencies, int[] exclusions) {
        getUpgrades().add(new UpgradeData(r, getUpgrades().size(), true, dependencies, exclusions));
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, boolean strict, int... dependencies) {
        getUpgrades().add(new UpgradeData(r, getUpgrades().size(), strict, dependencies));
    }

    default void addUpgradeData(AbstractCard card, UpgradeRunnable r, boolean strict, int[] dependencies, int[] exclusions) {
        getUpgrades().add(new UpgradeData(r, getUpgrades().size(), strict, dependencies, exclusions));
    }

    default void addUpgradeData(UpgradeRunnable r) {
        getUpgrades().add(new UpgradeData(r, getUpgrades().size()));
    }

    default void setExclusions(int... exclusiveIndices) {
        for (int i : exclusiveIndices) {
            UpgradeData d = getUpgrades().get(i);
            for (int e : exclusiveIndices) {
                if (e != i) {
                    d.exclusions.add(e);
                }
            }
        }
    }

    default void setDependencies(boolean strict, int upgradeIndex, int... requiredIndices) {
        UpgradeData d = getUpgrades().get(upgradeIndex);
        d.strict = strict;
        for (int i : requiredIndices) {
            d.dependencies.add(i);
        }
    }

    default void processUpgrade() {
        //Get the upgrades
        ArrayList<UpgradeData> upgrades = getUpgrades();
        //Get the next upgrade index
        int i = MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(this);
        //If it is -1, then this means grab a random available upgrade
        if (i == -1) {
            ArrayList<UpgradeData> validUpgrades = upgrades.stream().filter(u -> !u.applied && u.canUpgrade(upgrades)).collect(Collectors.toCollection(ArrayList::new));
            if (!validUpgrades.isEmpty()) {
                if (SingleCardViewPopup.isViewingUpgrade || AbstractDungeon.cardRandomRng == null || AbstractDungeon.player == null) {
                    i = validUpgrades.get(0).index;
                } else {
                    i = validUpgrades.get(AbstractDungeon.cardRandomRng.random(validUpgrades.size()-1)).index;
                }

            }
        }

        //If the upgrade isn't already applied, do it.
        if (i != -1 && upgrades.size() > i && !upgrades.get(i).applied) {
            updateName();
            if (this instanceof AbstractCard) {
                ((AbstractCard) this).timesUpgraded += (1 << i);
                ((AbstractCard) this).upgraded = true;
            }
            upgrades.get(i).upgrade();
        }

        //Default back to a random upgrade
        MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(this, -1);
    }

}
