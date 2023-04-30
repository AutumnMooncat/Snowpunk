package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.WidgetsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SilverBells extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SilverBells.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 4, WIDGETS = 2;

    public SilverBells() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber = magicNumber = WIDGETS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();
        Wiz.applyToSelf(new WidgetsPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(3));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeMagicNumber(2));
    }
}