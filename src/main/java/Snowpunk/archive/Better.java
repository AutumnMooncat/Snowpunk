package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.*;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Better extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Better.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2, UP_COST = 1;

    private boolean onPlay = false;

    public Better() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BetterPower(p, magicNumber));
        if (onPlay)
            Wiz.atb(new ArmamentsAction(true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            isInnate = true;
            info = baseInfo = 0;
            upgradeInfo(1);
        });
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> {
            onPlay = true;
            uDesc();
        });
    }
}