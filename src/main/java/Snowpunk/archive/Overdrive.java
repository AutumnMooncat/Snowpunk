package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.OverdrivePower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Overdrive extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Overdrive.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int STACKS = 1;
    private static final int UP_STACKS = 1;

    public Overdrive() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new OverdrivePower(p, magicNumber));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(UP_STACKS), 0, 1);
    }
}