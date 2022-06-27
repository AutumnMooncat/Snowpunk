package Snowpunk.util;

import java.util.ArrayList;

public class UpgradeData {
    public boolean applied = false;
    public int index;
    public UpgradeRunnable upgradeRunnable;
    public ArrayList<Integer> dependencies = new ArrayList<>();

    public UpgradeData(UpgradeRunnable runnable, int index, int... dependencies) {
        this.upgradeRunnable = runnable;
        this.index = index;
        for (int i : dependencies) {
            this.dependencies.add(i);
        }
    }

    public boolean canUpgrade(ArrayList<UpgradeData> upgrades) {
        if (applied) {
            return false;
        }
        for (int i : dependencies) {
            if (upgrades.size() <= i || !upgrades.get(i).applied) {
                return false;
            }
        }
        return true;
    }

    public void upgrade() {
        upgradeRunnable.doUpgrade();
        applied = true;
    }
}
