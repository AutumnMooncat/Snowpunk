package Snowpunk.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class UpgradeData {
    public boolean applied = false, strict = true;
    public int index;
    public UpgradeRunnable upgradeRunnable;
    public ArrayList<Integer> dependencies = new ArrayList<>(), exclusions = new ArrayList<>();
    public AbstractCard alias;
    public String upgradeName;
    public String upgradeDescription;

    public UpgradeData(UpgradeRunnable runnable, int index, int... dependencies) {
        this(runnable, index, null, dependencies, true, new int[]{});
    }

    public UpgradeData(UpgradeRunnable runnable, int index, AbstractCard alias, int[] dependencies, boolean strict, int[] exclusions) {
        this.upgradeRunnable = runnable;
        this.index = index;
        this.alias = alias;
        this.strict = strict;
        for (int i : dependencies) {
            this.dependencies.add(i);
        }
        for (int i : exclusions) {
            this.exclusions.add(i);
        }
    }

    public UpgradeData(UpgradeRunnable runnable, int index, String name, String description, int... dependencies) {
        this.upgradeRunnable = runnable;
        this.index = index;
        this.upgradeName = name;
        this.upgradeDescription = description;
        for (int i : dependencies) {
            this.dependencies.add(i);
        }
    }

    public boolean canUpgrade(ArrayList<UpgradeData> upgrades) {
        if (applied) {
            return false;
        }

        boolean dependencyCheck = false, exclusionCheck = true;

        for (int i : dependencies) {

            if (strict && (upgrades.size() <= i || !upgrades.get(i).applied))
                return false;

            if (upgrades.get(i).applied)
                dependencyCheck = true;
        }
        if (dependencies.size() == 0)
            dependencyCheck = true;

        for (int i : exclusions) {
            if (upgrades.get(i).applied)
                return false;
        }

        return dependencyCheck && exclusionCheck;
    }

    public void upgrade() {
        upgradeRunnable.doUpgrade();
        applied = true;
    }
}
