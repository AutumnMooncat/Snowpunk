package Snowpunk.cards;

import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static Snowpunk.SnowpunkMod.makeID;

public class Blueprint extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Blueprint.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private boolean freeUpgrade = false;

    public Blueprint() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        Wiz.atb(new MakeCopyInHandAction(freeUpgrade));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> {
            freeUpgrade = true;
            uDesc();
        });
        addUpgradeData(() -> {
            exhaust = false;
            upgradeInfo(1);
        });
    }
}