package Snowpunk.cutContent;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.HeatGeneratorPower;
import Snowpunk.powers.HeatTransferPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class HeatTransfer extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(HeatTransfer.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int BURN = 3;
    private static final int UP_BURN = 2;

    private boolean heatGenerator = false;

    public HeatTransfer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BURN;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (heatGenerator) {
            Wiz.applyToSelf(new HeatGeneratorPower(p, magicNumber));
        } else {
            Wiz.applyToSelf(new HeatTransferPower(p, magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(UP_BURN);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            isInnate = true;
            if (heatGenerator) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            } else {
                uDesc();
            }
        });
        addUpgradeData(() -> upgradeMagicNumber(UP_BURN));
        addUpgradeData(() -> {
            heatGenerator = true;
            if (isInnate) {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            } else {
                rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            this.name = cardStrings.EXTENDED_DESCRIPTION[2];
            initializeTitle();
            initializeDescription();
        });
    }
}