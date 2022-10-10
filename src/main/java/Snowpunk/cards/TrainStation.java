package Snowpunk.cards;

import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TrainStation extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TrainStation.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 0;

    public boolean freeUpgrade = false;

    public TrainStation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        exhaust = true;
        freeUpgrade = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new MakeCopyInHandAction(true, freeUpgrade));
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