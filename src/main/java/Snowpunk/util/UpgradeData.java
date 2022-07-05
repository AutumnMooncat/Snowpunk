package Snowpunk.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class UpgradeData {
    public boolean applied = false;
    public int index;
    public UpgradeRunnable upgradeRunnable;
    public ArrayList<Integer> dependencies = new ArrayList<>();
    public AbstractCard alias;

    public UpgradeData(UpgradeRunnable runnable, int index, int... dependencies) {
        this(runnable, index, null, dependencies);
    }

    public UpgradeData(UpgradeRunnable runnable, int index, AbstractCard alias, int... dependencies) {
        this.upgradeRunnable = runnable;
        this.index = index;
        this.alias = alias;
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
