package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballNextTurnPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class BurningFury extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BurningFury.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 0, FIRE = 2, UP_FIRE = 1;


    public BurningFury() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = FIRE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FireballNextTurnPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> upgradeMagicNumber(UP_FIRE));
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, 1);
        });
    }
}