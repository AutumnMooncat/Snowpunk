package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.*;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Clockwork extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Clockwork.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    boolean increaseG = false;
    public Clockwork() {
        super(ID, COST, TYPE, RARITY, TARGET);
        increaseG = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ClockworkPower(p, 1));
        if (increaseG)
            Wiz.applyToSelf(new CrankPower(Wiz.adp(), 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            increaseG = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}