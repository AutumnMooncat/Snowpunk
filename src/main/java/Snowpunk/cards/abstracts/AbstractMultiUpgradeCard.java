package Snowpunk.cards.abstracts;

import Snowpunk.patches.MkPatches;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.MultiUpgradePatches;
import com.evacipated.cardcrawl.mod.stslib.util.UpgradeData;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    public void upp() {
    }

    @Override
    public void processUpgrade() {
        ArrayList<UpgradeData> upgrades = this.getUpgrades();
        int i = MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.get(this);
        if (i == -1) {
            ArrayList<UpgradeData> validUpgrades = upgrades.stream().filter((u) -> {
                return !u.applied && u.canUpgrade(upgrades);
            }).collect(Collectors.toCollection(ArrayList::new));
            if (!validUpgrades.isEmpty())
                i = (validUpgrades.get(0)).index;
        }

        if (i != -1 && upgrades.size() > i && !(upgrades.get(i)).applied) {
            timesUpgraded += 1 << i;
            upgraded = true;
            (upgrades.get(i)).upgrade();
            updateName();
        }

        MultiUpgradePatches.MultiUpgradeFields.upgradeIndex.set(this, -1);
    }

}
