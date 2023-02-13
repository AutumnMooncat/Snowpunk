package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SparePartsPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Workshop extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Workshop.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, SPIRIT = 8, UP_SPIRIT = 4;


    public Workshop() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SPIRIT;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SparePartsPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_SPIRIT));
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}